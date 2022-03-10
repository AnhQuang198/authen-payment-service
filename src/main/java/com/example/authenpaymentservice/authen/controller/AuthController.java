package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.model.request.LoginRequest;
import com.example.authenpaymentservice.authen.model.request.OtpRequest;
import com.example.authenpaymentservice.authen.model.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody OtpRequest otpRequest) {
        return null;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OtpRequest otpRequest) {
        return null;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword() {
        return null;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(
            @RequestHeader("x-refresh-token") String refreshToken
    ) {
        return authService.generateToken(refreshToken);
    }


}
