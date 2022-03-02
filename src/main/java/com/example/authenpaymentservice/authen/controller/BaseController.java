package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.security.jwt.JwtTokenProvider;
import com.example.authenpaymentservice.authen.service.AuthService;
import com.example.authenpaymentservice.authen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired protected AuthService authService;
    @Autowired protected UserService userService;
    @Autowired protected JwtTokenProvider tokenProvider;
}
