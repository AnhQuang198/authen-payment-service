package com.example.authenpaymentservice.authen.security.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpInfo {
    private String email;
    private String otp;
}
