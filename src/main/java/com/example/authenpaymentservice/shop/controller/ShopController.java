package com.example.authenpaymentservice.shop.controller;

import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/v1/shops")
public class ShopController extends BaseController{
    @GetMapping
    public ResponseEntity<?> getShops(
            @RequestHeader("x-auth-token") String token,
            @RequestParam(value = "state", defaultValue = "PENDING") String shopState,
            @RequestParam(value = "name", required = false) String shopName,
            Pageable pageable
    ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.getShops(userId, shopName, shopState, pageable);
    }

    @PostMapping
    public ResponseEntity<?> createShop(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopCreateRequest shopCreateRequest
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.createShop(userId, shopCreateRequest);
    }

    @PostMapping("/address")
    public ResponseEntity<?> createAddress(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopAddressRequest request
    ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return null;
    }
}
