package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.producer.OtpProducer;
import com.example.authenpaymentservice.authen.repository.UserRepository;
import com.example.authenpaymentservice.authen.security.jwt.JwtTokenProvider;
import com.example.authenpaymentservice.authen.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public class BaseService {
    @Autowired protected AuthenticationManager authenticationManager;
    @Autowired protected UserRepository userRepository;
    @Autowired protected JwtTokenProvider jwtTokenProvider;
    @Autowired protected CacheUtils cacheUtils;
    @Autowired protected OtpProducer otpProducer;
}
