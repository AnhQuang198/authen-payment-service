package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.dtos.ShopCreateDTO;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.enums.ShopState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShopService extends BaseService {

    public ResponseEntity<?> createShop(int userId, ShopCreateDTO shopCreateDTO) {
        checkUserState(userId);
        if (checkShopExisted(userId)) {
            throw new ResourceNotFoundException(Message.SHOP_EXISTED);
        }
        Shop shop = new Shop();
        shop.setId(userId);
        shop.setName(shopCreateDTO.getName());
        shop.setCoverUrl(shopCreateDTO.getCoverUrl());
        shop.setAvatarUrl(shopCreateDTO.getAvatarUrl());
        shop.setDescription(shopCreateDTO.getDescription());
        shop.setState(ShopState.PENDING);
        shopRepository.save(shop);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
