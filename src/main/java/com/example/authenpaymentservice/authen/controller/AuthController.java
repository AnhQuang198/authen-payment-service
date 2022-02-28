package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.dtos.LoginDTO;
import com.example.authenpaymentservice.authen.dtos.OtpDTO;
import com.example.authenpaymentservice.authen.dtos.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController extends BaseController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody OtpDTO otpDTO) {
        return null;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody OtpDTO otpDTO) {
        return null;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword() {
        return null;
    }


}
