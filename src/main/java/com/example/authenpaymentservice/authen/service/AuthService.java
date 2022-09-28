package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.AuthProvider;
import com.example.authenpaymentservice.authen.enums.OTPType;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.model.request.*;
import com.example.authenpaymentservice.authen.model.response.LoginResponse;
import com.example.authenpaymentservice.authen.security.data.CustomUserDetails;
import com.example.authenpaymentservice.authen.security.data.OtpInfo;
import com.example.authenpaymentservice.authen.utils.CacheKey;
import com.example.authenpaymentservice.authen.utils.Common;
import com.example.authenpaymentservice.authen.utils.UserData;
import com.example.authenpaymentservice.exception.BadRequestException;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class AuthService extends BaseService implements UserDetailsService {
    private static final String TOKEN_TYPE = "x-auth-token";

    @Value("${jwt.otp-expire-time}")
    private int otpExpireTime;

    public ResponseEntity<?> register(RegisterRequest request) {
        try {
            User user = userRepository.findUserByEmail(request.getEmail());
            if (Objects.nonNull(user)) {
                throw new BadRequestException(Message.USERNAME_EXITED);
            }
            saveUser(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        LoginResponse response;
        try {
            // auth
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get user in authentication principal
            User user = UserData.getCurrentUserLogin(authentication);
            String token = jwtTokenProvider.generateToken(user);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user);
            cacheRefreshToken(user.getId(), refreshToken);
            response = new LoginResponse(token, refreshToken, TOKEN_TYPE, jwtTokenProvider.getTokenExpireTime());
        } catch (DisabledException ex) {
            throw new UnauthorizedException(Message.ACCOUNT_NON_ACTIVE);
        } catch (LockedException ex) {
            throw new UnauthorizedException(Message.ACCOUNT_LOCKED);
        } catch (Exception ex) {
            throw new UnauthorizedException(Message.PASSWORD_INVALID);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> sendOtp(OTPRequest dto) {
        int otp = Common.generateOTP();
        try {
            OtpInfo otpInfo = new OtpInfo(dto.getEmail(), String.valueOf(otp));
            if (dto.getOtpType().equals(OTPType.REGISTER.toString())) {
                User user = userRepository.findUserByEmail(dto.getEmail());
                if (user.getState().equals(UserState.ACTIVE)) {
                    throw new ResourceNotFoundException(Message.ACCOUNT_ACTIVE);
                }
                String key = CacheKey.genMailOtp(dto.getEmail());
                cacheUtils.set(key, otpInfo, otpExpireTime);
            } else if (dto.getOtpType().equals(OTPType.FORGOT.toString())) {
                User user = userRepository.findUserByEmail(dto.getEmail());
                String key = CacheKey.genForgotPasswordOtp(user.getEmail(), user.getId());
                cacheUtils.set(key, otpInfo, otpExpireTime);
            }
        } catch (Exception e) {
            log.error("Send OTP error: {0}", e);
            e.printStackTrace();
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> forgotPassword(ForgotPassRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail());
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        String key = CacheKey.genForgotPasswordOtp(user.getEmail(), user.getId());
        String tokenKey = CacheKey.genRefreshToken(user.getId());
        try {
            if (cacheUtils.isExisted(key)) {
                OtpInfo otpInfo = cacheUtils.get(key, OtpInfo.class);
                validOtp(otpInfo, request.getOtp());
                saveUserPassword(user, request);
                cacheUtils.del(key);
                cacheUtils.del(tokenKey);
            } else {
                throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
        }
        return ResponseEntity.ok(true);
    }

    public void sendOtpRegister(String email) {
        OTPRequest request = new OTPRequest();
        request.setEmail(email);
        request.setOtpType(OTPType.REGISTER.toString());
        sendOtp(request);
    }

    public ResponseEntity<?> verifyOtp(OTPVerifyRequest dto) {
        try {
            String key = CacheKey.genMailOtp(dto.getEmail());
            OtpInfo otpInfo = cacheUtils.get(key, OtpInfo.class);
            if (Objects.isNull(otpInfo)) {
                throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
            }
            //compare otp in cache and otp send
            if (!otpInfo.getOtp().equals(dto.getOtp())) {
                throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
            }
            updateStatusUser(dto.getEmail());
            cacheUtils.del(key);
        } catch (Exception e) {
            throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
        }
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<?> generateToken(String refreshToken) {
        //check refreshToken expired > generate new Token and
        LoginResponse response;
        try {
            String refreshTokenDecode = Common.decode(refreshToken);
            int userId = Integer.parseInt(refreshTokenDecode.substring(Math.max(refreshTokenDecode.length() - 1, 0)));
            User user = userRepository.findUserById(userId);
            if (Objects.isNull(user) || user.getState().equals(UserState.NON_ACTIVE) || user.isLocked()) {
                throw new ResourceNotFoundException(Message.ACCOUNT_LOCKED);
            }

            String key = CacheKey.genRefreshToken(userId);
            String savedRefreshToken = cacheUtils.get(key, String.class);
            if (Objects.isNull(savedRefreshToken)) {
                throw new ResourceNotFoundException(Message.NOT_FOUND);
            }
            if (!savedRefreshToken.equals(refreshToken)) {
                throw new ResourceNotFoundException(Message.NOT_FOUND);
            }
            String newAccessToken = jwtTokenProvider.generateToken(user);
            response = new LoginResponse(newAccessToken, refreshToken, TOKEN_TYPE, jwtTokenProvider.getTokenExpireTime());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
//    authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
        return new CustomUserDetails(user, authorities);
    }

    public UserDetails loadUserById(int id) {
        User user = userRepository.findUserById(id);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.MEMBER.toString()));
        authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
        return new CustomUserDetails(user, authorities);
    }

    private void saveUser(RegisterRequest request) {
        User user = new User();
        String passwordEncrypt = encodePassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncrypt);
        user.setAuthProvider(AuthProvider.LOCAL);
        userRepository.save(user);
        sendOtpRegister(user.getEmail());
    }

    private void updateStatusUser(String email) {
        User user = userRepository.findUserByEmail(email);
        user.setState(UserState.ACTIVE);
        userRepository.saveAndFlush(user);
    }

    public void cacheRefreshToken(int userId, String refreshToken) throws IOException {
        String key = CacheKey.genRefreshToken(userId);
        cacheUtils.set(key, refreshToken, jwtTokenProvider.getRefreshExpireTime());
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private void validOtp(OtpInfo otpInfo, String otp) {
        if (!otpInfo.getOtp().equals(otp)) {
            throw new ResourceNotFoundException(Message.OTP_NOT_VALID);
        }
    }

    private void saveUserPassword(User user, ForgotPassRequest dto) {
        comparePassword(dto.getPassword(), dto.getConfirmPassword());
        String passwordEncode = encodePassword(dto.getPassword());
        user.setPassword(passwordEncode);
        userRepository.saveAndFlush(user);
    }

    private void comparePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ResourceNotFoundException(Message.REPASS_NOT_VALID);
        }
    }
}
