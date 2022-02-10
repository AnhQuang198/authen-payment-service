package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired protected UserRepository userRepository;
}
