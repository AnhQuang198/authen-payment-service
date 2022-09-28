package com.example.authenpaymentservice.authen.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ForgotPassRequest {
    @NotNull
    @Size(min = 6, max = 50)
//    @EmailConstraint
    private String email;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @Size(min = 6, max = 50)
    private String confirmPassword;

    @NotNull
    @Size(min = 6, max = 7)
    private String otp;
}
