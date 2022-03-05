package com.example.authenpaymentservice.shop.dtos;

import com.example.authenpaymentservice.shop.enums.ShopState;
import lombok.Data;

@Data
public class ShopStatusDTO {
    private Integer id;
    private boolean isLocked;
    private ShopState state;

    public ShopStatusDTO(int id, boolean isLocked, ShopState state) {
        this.id = id;
        this.isLocked = isLocked;
        this.state = state;
    }
}
