package com.example.authenpaymentservice.authen.dtos;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String name;
    private String dob;
    private String avatarUrl;
    private String coverUrl;
    private Boolean gender;
}
