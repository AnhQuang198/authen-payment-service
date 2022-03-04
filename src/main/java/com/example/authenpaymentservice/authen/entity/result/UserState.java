package com.example.authenpaymentservice.authen.entity.result;

import com.example.authenpaymentservice.shop.enums.ShopState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserState {
    private Integer id;
    private boolean isLocked;
    private ShopState state;
}
