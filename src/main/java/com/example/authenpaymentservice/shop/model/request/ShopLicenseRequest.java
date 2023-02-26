package com.example.authenpaymentservice.shop.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShopLicenseRequest {
    private Long licenseId;
    @NotNull
    private Long shopId;
    private String frontIdentityCardUrl;
    private String backIdentityCardUrl;
    private String ownerName;
    private String identityNumber;
    private String identityReleaseDate;
    private String businessLicenseUrl;
    private String taxCode;
    private String companyName;
    @NotNull
    private String action;
}
