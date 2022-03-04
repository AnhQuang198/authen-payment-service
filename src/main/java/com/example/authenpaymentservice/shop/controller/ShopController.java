package com.example.authenpaymentservice.shop.controller;

import com.example.authenpaymentservice.shop.dtos.ShopCreateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/shop")
public class ShopController extends BaseController{

    @PostMapping("/create")
    public ResponseEntity<?> createShop(
            @RequestHeader("x-auth-token") String token,
            @RequestBody ShopCreateDTO shopCreateDTO
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return shopService.createShop(userId, shopCreateDTO);
    }
}
