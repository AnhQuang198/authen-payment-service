package com.example.authenpaymentservice.authen.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class OtpRequest {
    @NotNull
    @Size(min = 6, max = 50)
    private String email;

    @NotNull
    @Size(min = 6, max = 7)
    private String otp;

    @JsonProperty("otp-type")
    private String otpType;
}
