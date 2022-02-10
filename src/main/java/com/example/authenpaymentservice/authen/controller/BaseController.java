package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired protected AuthService authService;
}
