package com.example.authenpaymentservice.shop.entity.result;

import com.example.authenpaymentservice.shop.enums.ShopState;
import lombok.Data;

@Data
public class ShopStatus {
    private Integer id;
    private boolean isLocked;
    private ShopState state;
}
