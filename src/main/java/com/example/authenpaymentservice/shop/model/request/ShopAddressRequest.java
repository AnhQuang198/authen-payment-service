package com.example.authenpaymentservice.shop.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShopAddressRequest {
    private Integer id;
    private String addDetail;
    private Integer cityId;
    private Integer wardId;
    private Integer districtId;

    @NotNull
    private String type;
}
