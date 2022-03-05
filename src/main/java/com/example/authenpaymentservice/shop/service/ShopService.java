package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.NoAccessException;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.enums.ShopState;
import com.example.authenpaymentservice.shop.model.response.ShopResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.Objects;

@Service
public class ShopService extends BaseService {
    public ResponseEntity<?> getShops(int userId, String shopName, String shopState, Pageable pageable) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        ShopResponse response = new ShopResponse();

        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<?> createShop(int userId, ShopCreateRequest request) {
        User user = checkUserState(userId);
        if (checkShopExisted(userId)) {
            throw new ResourceNotFoundException(Message.SHOP_EXISTED);
        }
        Shop shop = new Shop();
        shop.setId(userId);
        shop.setName(request.getName());
        shop.setCoverUrl(request.getCoverUrl());
        shop.setAvatarUrl(request.getAvatarUrl());
        shop.setDescription(request.getDescription());
        shop.setState(ShopState.PENDING);
        shopRepository.save(shop);
        updateUserRole(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void updateUserRole(User user) {
        if (Objects.nonNull(user)) {
            user.setRole(UserRole.SELLER);
            userRepository.save(user);
        }
    }
}
