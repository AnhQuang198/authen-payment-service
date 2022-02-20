package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.dtos.LoginDTO;
import com.example.authenpaymentservice.authen.dtos.RegisterDTO;
import com.example.authenpaymentservice.authen.enums.AuthProvider;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.model.CustomUserDetails;
import com.example.authenpaymentservice.authen.model.TokenInfo;
import com.example.authenpaymentservice.authen.response.LoginResponse;
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

  public ResponseEntity<?> register(RegisterDTO registerDTO) {
    User user = userRepository.findUserByEmail(registerDTO.getEmail());
    if (Objects.nonNull(user)) {
      throw new BadRequestException(Message.USERNAME_EXITED);
    }
    saveUser(registerDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  public ResponseEntity<?> login(LoginDTO loginDTO) {
    LoginResponse response;
    try {
      // auth
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      // get user in authentication principal
      CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
      User user = customUser.getUser();
      TokenInfo tokenInfo = new TokenInfo(user.getId(), user.getRole(), user.getState());
      String token = jwtTokenProvider.generateToken(tokenInfo);
      String refreshToken = jwtTokenProvider.generateRefreshToken(tokenInfo);
      response = new LoginResponse(token, refreshToken, "x-auth-token");
    } catch (DisabledException ex) {
      throw new UnauthorizedException(Message.ACCOUNT_NON_ACTIVE);
    } catch (LockedException ex) {
      throw new UnauthorizedException(Message.ACCOUNT_LOCKED);
    } catch (Exception ex) {
      throw new UnauthorizedException(Message.PASSWORD_INVALID);
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
    authorities.add(new SimpleGrantedAuthority(UserRole.MEMBER.toString()));
    authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
    return new CustomUserDetails(user, authorities);
  }

  public UserDetails loadUserById(int id) {
    User user = userRepository.findUserById(id);
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(UserRole.MEMBER.toString()));
    authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
    return new CustomUserDetails(user, authorities);
  }

  private void saveUser(RegisterDTO registerDTO) {
    User user = new User();
    String passwordEncrypt = encodePassword(registerDTO.getPassword());
    user.setEmail(registerDTO.getEmail());
    user.setPassword(passwordEncrypt);
    user.setAuthProvider(AuthProvider.LOCAL);
    userRepository.save(user);
  }

  private String encodePassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }
}
