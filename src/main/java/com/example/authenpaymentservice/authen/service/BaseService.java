package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public class BaseService {
    @Autowired protected AuthenticationManager authenticationManager;
    @Autowired protected UserRepository userRepository;
}
