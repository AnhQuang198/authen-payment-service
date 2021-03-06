package com.example.authenpaymentservice.authen.dtos;

import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import lombok.Data;

@Data
public class UserStatusDTO {
    private Integer id;
    private boolean isLocked;
    private UserState state;
    private UserRole role;

    public UserStatusDTO(int id, boolean isLocked, UserState state, UserRole role) {
        this.id = id;
        this.isLocked = isLocked;
        this.state = state;
        this.role = role;
    }
}
