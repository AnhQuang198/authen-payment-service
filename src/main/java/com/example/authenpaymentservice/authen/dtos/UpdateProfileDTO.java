package com.example.authenpaymentservice.authen.dtos;

import lombok.Data;

@Data
public class UpdateProfileDTO {
    private String name;
    private String dob;
    private String avatarUri;
    private String coverUri;
}
