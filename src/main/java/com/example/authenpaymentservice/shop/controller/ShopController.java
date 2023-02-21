package com.example.authenpaymentservice.shop.controller;

import com.example.authenpaymentservice.shop.model.request.CommonRequest;
import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import com.example.authenpaymentservice.shop.model.request.ShopLicenseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/shops")
public class ShopController extends BaseController {
    private static final String TOKEN_TYPE = "x-auth-token";

    @GetMapping("{shopId}")
    public ResponseEntity<?> getShop(
            @RequestHeader(TOKEN_TYPE) String token,
            @PathVariable Integer shopId
    ) {
        return shopService.getShop(shopId);
    }

    @GetMapping
    public ResponseEntity<?> getShops(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody CommonRequest request
    ) {
        long userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.getShops(userId, request);
    }

    @PostMapping
    public ResponseEntity<?> createShop(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody @Valid ShopCreateRequest shopCreateRequest
    ) {
        long userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.createShop(userId, shopCreateRequest);
    }

    @PutMapping("/approve/{shopId}")
    public ResponseEntity<?> approveShop(
            @RequestHeader(TOKEN_TYPE) String token,
            @PathVariable("shopId") Integer shopId
    ) {
        long userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.approveShop(userId, shopId);
    }

    @PostMapping("/address")
    public ResponseEntity<?> createAddress(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody @Valid ShopAddressRequest request
    ) {
        long shopId = tokenProvider.getUserIdFromJWT(token);
        return shopService.addressProcess(shopId, request);
    }

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody @Valid ShopAddressRequest request
    ) {
        long shopId = tokenProvider.getUserIdFromJWT(token);
        return shopService.addressProcess(shopId, request);
    }

    @PostMapping("/license")
    public ResponseEntity<?> addLicense(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody @Valid ShopLicenseRequest request
    ) {
        long shopId = tokenProvider.getUserIdFromJWT(token);
        return shopService.addLicense(shopId, request);
    }
}
