package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.dtos.UserDTO;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.model.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService extends BaseService implements UserDetailsService {

    public ResponseEntity<?> login(UserDTO userDTO) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<String>(HttpStatus.OK);
        return null;
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
}
