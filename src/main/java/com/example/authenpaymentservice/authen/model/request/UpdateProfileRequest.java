package com.example.authenpaymentservice.authen.model.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String dob;
    private String avatarUrl;
    private String coverUrl;
    private Boolean gender;
}
