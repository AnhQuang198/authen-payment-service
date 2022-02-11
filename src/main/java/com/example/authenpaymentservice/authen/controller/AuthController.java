package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.dtos.LoginDTO;
import com.example.authenpaymentservice.authen.dtos.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController extends BaseController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
