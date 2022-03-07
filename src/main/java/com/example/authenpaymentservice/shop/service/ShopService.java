package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.NoAccessException;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.entity.ShopAddress;
import com.example.authenpaymentservice.shop.enums.ShopState;
import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import com.example.authenpaymentservice.shop.model.response.ShopResponse;
import com.example.authenpaymentservice.shop.model.response.data.Metadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class ShopService extends BaseService {
    public ResponseEntity<?> getShops(int userId, String shopName, String shopState, Pageable pageable) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        ShopResponse response = new ShopResponse();
        Page<ShopDTO> pages;
        ShopState state = ShopState.valueOf(shopState.toUpperCase());
        if (Objects.nonNull(shopName)) {
            pages = shopRepository.getShopsByName(state, shopName, pageable);
        } else {
            pages = shopRepository.getShops(state, pageable);
        }
        response.setShops(pages.getContent());
        response.setMetadata(Metadata.of(pages));
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

    public ResponseEntity<?> approveShop(int userId, int shopId) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        Shop shop = shopRepository.findShopById(shopId);
        shop.setState(ShopState.APPROVED);
        shopRepository.save(shop);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> addressProcess(int shopId, ShopAddressRequest request) {
        checkShopExisted(shopId);
        ShopAddress shopAddress = new ShopAddress();
        if (request.getType().equalsIgnoreCase("update")) {
            //get current address
            shopAddress = shopAddressRepository.findByIdAndShopId(request.getId(), shopId);
        }
        shopAddress.setShopId(shopId);
        shopAddress.setCityId(request.getCityId());
        shopAddress.setDistrictId(request.getDistrictId());
        shopAddress.setWardId(request.getWardId());
        shopAddress.setAddDetail(request.getAddDetail());
        shopAddressRepository.save(shopAddress);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void updateUserRole(User user) {
        if (Objects.nonNull(user)) {
            user.setRole(UserRole.SELLER);
            userRepository.save(user);
        }
    }
}
