package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.model.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService extends BaseService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.toString()));
        authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
        return new CustomUserDetails(user, authorities);
    }
}
