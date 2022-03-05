package com.example.authenpaymentservice.shop.dtos;

import com.example.authenpaymentservice.shop.enums.ShopState;
import lombok.Data;

@Data
public class ShopDTO {
    private Integer shopId;
    private String shopName;
    private String email;
    private String phone;
    private ShopState state;
    private Boolean isLocked;

}
