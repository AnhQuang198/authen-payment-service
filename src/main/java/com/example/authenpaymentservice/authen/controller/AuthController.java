package com.example.authenpaymentservice.authen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthController {

    @GetMapping
    public String helloWorld() {
        return "hello world";
    }
}
