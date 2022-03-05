package com.example.authenpaymentservice.authen.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotNull
    @Size(min = 6, max = 50)
    private String email;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;
}
