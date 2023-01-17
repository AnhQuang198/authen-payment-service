package com.example.authenpaymentservice.shop.controller;

import com.example.authenpaymentservice.shop.model.request.CommonRequest;
import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/shops")
public class ShopController extends BaseController{
    @GetMapping("{shopId}")
    public ResponseEntity<?> getShop(
            @RequestHeader("x-auth-token") String token,
            @PathVariable long shopId
    ) {
        return shopService.getShop(shopId);
    }

    @GetMapping
    public ResponseEntity<?> getShops(
            @RequestHeader("x-auth-token") String token,
            @RequestBody CommonRequest request
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.getShops(userId, request);
    }

    @PostMapping
    public ResponseEntity<?> createShop(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopCreateRequest shopCreateRequest
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.createShop(userId, shopCreateRequest);
    }

    @PutMapping("/approve/{shopId}")
    public ResponseEntity<?> approveShop(
            @RequestHeader("x-auth-token") String token,
            @PathVariable("shopId") Integer shopId
    ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.approveShop(userId, shopId);
    }

    @PostMapping("/address")
    public ResponseEntity<?> createAddress(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopAddressRequest request
    ) {
        int shopId = tokenProvider.getUserIdFromJWT(token);
        return shopService.addressProcess(shopId, request);
    }

    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopAddressRequest request
    ) {
        int shopId = tokenProvider.getUserIdFromJWT(token);
        return shopService.addressProcess(shopId, request);
    }
}
