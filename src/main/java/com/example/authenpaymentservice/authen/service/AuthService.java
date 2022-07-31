package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.model.request.LoginRequest;
import com.example.authenpaymentservice.authen.model.request.RegisterRequest;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.AuthProvider;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.security.data.CustomUserDetails;
import com.example.authenpaymentservice.authen.model.response.LoginResponse;
import com.example.authenpaymentservice.authen.utils.UserData;
import com.example.authenpaymentservice.exception.BadRequestException;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.UnauthorizedException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthService extends BaseService implements UserDetailsService {

  public ResponseEntity<?> register(RegisterRequest request) {
    User user = userRepository.findUserByEmail(request.getEmail());
    if (Objects.nonNull(user)) {
      throw new BadRequestException(Message.USERNAME_EXITED);
    }
    saveUser(request);
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
      response = new LoginResponse(token, refreshToken, "x-auth-token", jwtTokenProvider.getTokenExpireTime());
    } catch (DisabledException ex) {
      throw new UnauthorizedException(Message.ACCOUNT_NON_ACTIVE);
    } catch (LockedException ex) {
      throw new UnauthorizedException(Message.ACCOUNT_LOCKED);
    } catch (Exception ex) {
      throw new UnauthorizedException(Message.PASSWORD_INVALID);
    }
    return ResponseEntity.ok(response);
  }

  public ResponseEntity<?> generateToken(String refreshToken) {
    //check refreshToken expired > generate new Token and
    return null;
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
  }

  private String encodePassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }
}
