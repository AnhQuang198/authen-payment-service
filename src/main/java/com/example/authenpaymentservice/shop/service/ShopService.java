package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.NoAccessException;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.entity.ShopAddress;
import com.example.authenpaymentservice.shop.enums.ShopState;
import com.example.authenpaymentservice.shop.model.Datatable;
import com.example.authenpaymentservice.shop.model.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.model.request.CommonRequest;
import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import com.example.authenpaymentservice.shop.model.response.ShopResponse;
import com.example.authenpaymentservice.shop.model.response.data.Metadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ShopService extends BaseService {
    public ResponseEntity<?> getShops(int userId, CommonRequest request) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        ShopResponse response = new ShopResponse();
        Datatable datatable = shopRepository.getShops(request);
        response.setShops((List<ShopDTO>) datatable.getData());
        response.setMetadata(Metadata.of(datatable));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getShop(long shopId) {
        Shop shop = shopRepository.getEntityManager().find(Shop.class, shopId);
        return ResponseEntity.ok(shop);
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
        shopRepository.saveOrUpdate(shop);
        updateUserRole(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public ResponseEntity<?> approveShop(int userId, int shopId) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        Shop shop = shopRepository.getEntityManager().find(Shop.class, shopId);
        shop.setState(ShopState.APPROVED);
        shopRepository.saveOrUpdate(shop);
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
