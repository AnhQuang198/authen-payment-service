package com.example.authenpaymentservice.shop.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShopLicenseApproveRequest {
    @NotNull
    private Long licenseId;

    @NotNull
    private String state;

    @NotNull
    private String rejectReason;
}
