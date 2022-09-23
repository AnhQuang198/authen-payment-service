package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.repository.UserRepository;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class BaseService {
    @Autowired protected ShopRepository shopRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected ShopAddressRepository shopAddressRepository;
    @Autowired protected CityRepository cityRepository;
    @Autowired protected DistrictRepository districtRepository;
    @Autowired protected WardRepository wardRepository;

    protected User checkUserState(int userId) {
        User user = userRepository.findUserById(userId);
        if (Objects.nonNull(user)) {
            if (user.isLocked()) {
                throw new ResourceNotFoundException(Message.ACCOUNT_LOCKED);
            } else if (user.getState().equals(UserState.NON_ACTIVE)) {
                throw new ResourceNotFoundException(Message.ACCOUNT_NON_ACTIVE);
            }
            return user;
        } else {
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
    }

    protected boolean checkShopExisted(int userId) {
//        Shop shop = shopRepository.findById(userId);
//        if (Objects.isNull(shop) || shop.isLocked()) {
//            return false;
//        }
        return true;
    }
}
