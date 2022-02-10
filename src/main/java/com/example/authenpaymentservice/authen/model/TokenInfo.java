package com.example.authenpaymentservice.authen.model;

import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {
    private Integer userId;
    private UserRole role;
    private UserState state;
}
