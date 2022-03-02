package com.example.authenpaymentservice.authen.utils;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.model.CustomUserDetails;
import org.springframework.security.core.Authentication;

public class UserData {

    //get current user login in system
    public static User getCurrentUserLogin(Authentication authentication) {
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
        return customUser.getUser();
    }

}
