package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.model.request.LoginRequest;
import com.example.authenpaymentservice.authen.model.request.OTPRequest;
import com.example.authenpaymentservice.authen.model.request.OTPVerifyRequest;
import com.example.authenpaymentservice.authen.model.request.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody @Valid OTPRequest otpRequest) {
        return authService.sendOtp(otpRequest);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody @Valid OTPVerifyRequest otpVerifyRequest) {
        return authService.verifyOtp(otpVerifyRequest);
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
