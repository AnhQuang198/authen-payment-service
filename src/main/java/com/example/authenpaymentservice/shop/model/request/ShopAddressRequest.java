package com.example.authenpaymentservice.shop.model.request;

import lombok.Data;

@Data
public class ShopAddressRequest {
    private Integer id;
    private String name;
    private String phone;
    private String addDetail;
    private Integer cityId;
    private Integer wardId;
    private Integer districtId;
    private String type;
}
