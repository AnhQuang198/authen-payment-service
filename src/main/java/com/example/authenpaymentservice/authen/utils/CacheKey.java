package com.example.authenpaymentservice.authen.utils;

import lombok.Data;

@Data
public class CacheKey {
    private static String prefix = "demo";

    public static String genRefreshToken(int userId) {
        return prefix + ":refreshToken:" + userId;
    }

    public static String genMailOtp(String email){
        return prefix + ":registerOtp:" + email;
    }

    public static String genForgotPasswordOtp(String username, int id){
        return prefix + ":forgotPassOtp:" + username + ":" + id;
    }
}
