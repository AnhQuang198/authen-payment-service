package com.example.authenpaymentservice.shop.model.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ShopDTO {
    private Integer shopId;
    private String shopName;
    private String state;
    private Boolean isLocked;
    private String avatarUrl;
    private String coverUrl;
    private String description;
    //user info
    private String email;
    private String phone;
    //city
    private Integer cityId;
    private String cityName;
    //district
    private Integer districtId;
    private String districtName;
    //ward
    private Integer wardId;
    private String wardName;
    private String addDetail;
    private Timestamp createdAt;
}
