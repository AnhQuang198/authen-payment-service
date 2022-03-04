package com.example.authenpaymentservice.shop.service;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.repository.UserRepository;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.shop.entity.Shop;
import com.example.authenpaymentservice.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class BaseService {
    @Autowired protected ShopRepository shopRepository;
    @Autowired protected UserRepository userRepository;

    protected void checkUserState(int userId) {
        User user = userRepository.getUserState(userId);
        if (Objects.nonNull(user)) {
            if (user.isLocked()) {
                throw new ResourceNotFoundException(Message.ACCOUNT_LOCKED);
            } else if (user.getState().equals(UserState.NON_ACTIVE)) {
                throw new ResourceNotFoundException(Message.ACCOUNT_NON_ACTIVE);
            }
        } else {
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
    }

    protected boolean checkShopExisted(int userId) {
        Shop shop = shopRepository.getShopStatus(userId);
        if (Objects.isNull(shop)) {
            return false;
        }
        return true;
    }
}
