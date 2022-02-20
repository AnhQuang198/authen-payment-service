package com.example.authenpaymentservice.authen.model;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;
    private List<SimpleGrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(User user, List<SimpleGrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.MEMBER.toString()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return checkUserState(user);
    }

    private boolean checkUserState(User user) {
        if (user.getState().equals(UserState.NON_ACTIVE)) {
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }
}
