package com.example.authenpaymentservice.authen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(value = "*", maxAge = 3600L)
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getProfile() {
        return null;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> get() {
        return null;
    }
}
