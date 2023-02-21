package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.exception.BadRequestException;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.NoAccessException;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.entity.ShopAddress;
import com.example.authenpaymentservice.shop.entity.ShopLicense;
import com.example.authenpaymentservice.shop.enums.ShopLicenseState;
import com.example.authenpaymentservice.shop.enums.ShopState;
import com.example.authenpaymentservice.shop.model.Datatable;
import com.example.authenpaymentservice.shop.model.dtos.ShopDTO;
import com.example.authenpaymentservice.shop.model.request.CommonRequest;
import com.example.authenpaymentservice.shop.model.request.ShopAddressRequest;
import com.example.authenpaymentservice.shop.model.request.ShopCreateRequest;
import com.example.authenpaymentservice.shop.model.request.ShopLicenseRequest;
import com.example.authenpaymentservice.shop.model.response.ShopResponse;
import com.example.authenpaymentservice.shop.model.response.data.Metadata;
import com.example.authenpaymentservice.shop.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ShopService extends BaseService {
    public ResponseEntity<?> getShops(long userId, CommonRequest request) {
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

    public ResponseEntity<?> getShop(int shopId) {
        Shop shop = shopRepository.getEntityManager().find(Shop.class, shopId);
        return ResponseEntity.ok(shop);
    }

    @Transactional
    public ResponseEntity<?> createShop(long userId, ShopCreateRequest request) {
        User user = checkUserState(userId);
        if (checkShopExisted(userId)) {
            throw new ResourceNotFoundException(Message.SHOP_EXISTED);
        }
        Shop shop = new Shop();
        CommonUtils.map(request, shop);
        shop.setId(userId);
        shop.setState(ShopState.PENDING);
        shopRepository.saveOrUpdate(shop);
        updateUserRole(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public ResponseEntity<?> approveShop(long userId, long shopId) {
        User user = checkUserState(userId);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw new NoAccessException(Message.NO_ACCESS_RESOURCE);
        }
        Shop shop = shopRepository.getEntityManager().find(Shop.class, shopId);
        shop.setState(ShopState.APPROVED);
        shopRepository.saveOrUpdate(shop);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<?> addressProcess(long shopId, ShopAddressRequest request) {
        if(checkShopExisted(shopId)) {
            throw new ResourceNotFoundException(Message.SHOP_NOT_FOUND);
        }
        ShopAddress shopAddress = new ShopAddress();
        if (request.getAction().equalsIgnoreCase("update")) {
            //get current address
            shopAddress = shopAddressRepository.findByIdAndShopId(request.getId(), shopId);
        }
        CommonUtils.map(request, shopAddress);
        shopAddressRepository.save(shopAddress);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    public ResponseEntity<?> addLicense(long shopId, ShopLicenseRequest request) {
        if(checkShopExisted(shopId)) {
            throw new ResourceNotFoundException(Message.SHOP_NOT_FOUND);
        }
        ShopLicense shopLicense = new ShopLicense();
        if (request.getAction().equalsIgnoreCase("update")) {
            //get current address
            shopLicense = shopLicenseRepository.getEntityManager().find(ShopLicense.class, request.getId());
            if (Objects.isNull(shopLicense)) {
                throw new BadRequestException(Message.DATA_NOT_FOUND);
            }
            if (shopLicense.getShopId() != shopId) {
                throw new BadRequestException(Message.SHOP_LICENSE_INVALID);
            }
        }
        CommonUtils.map(request, shopLicense);
        shopLicense.setState(ShopLicenseState.PENDING);
        shopLicenseRepository.saveOrUpdate(shopLicense);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    private void updateUserRole(User user) {
        if (Objects.nonNull(user)) {
            user.setRole(UserRole.SELLER);
            userRepository.save(user);
        }
    }
}
