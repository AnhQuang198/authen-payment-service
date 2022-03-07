package com.example.authenpaymentservice.shop.dtos;

import com.example.authenpaymentservice.shop.enums.ShopState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopDTO {
    private Integer id;
    private String shopName;
    private String email;
    private String phone;
    private ShopState state;
    private Boolean isLocked;
    private LocalDateTime createdAt;

    public ShopDTO(Integer id, String shopName, String email, String phone, ShopState state, Boolean isLocked, LocalDateTime createdAt) {
        this.id = id;
        this.shopName = shopName;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.isLocked = isLocked;
        this.createdAt = createdAt;
    }
}
