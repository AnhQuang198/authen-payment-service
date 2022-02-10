package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AuthController extends BaseController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return authService.login(userDTO);
    }

    @GetMapping
    public String helloWorld() {
        return "hello world";
    }
}
