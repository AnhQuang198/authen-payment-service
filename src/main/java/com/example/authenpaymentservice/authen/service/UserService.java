package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.dtos.UpdateProfileDTO;
import com.example.authenpaymentservice.authen.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService extends BaseService{

    public ResponseEntity<?> getUser(Integer userId) {
        User user = new User();
        if (Objects.nonNull(userId)) {
            user = userRepository.findUserById(userId);
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> updateProfile(UpdateProfileDTO dto, Integer userId) {
        return null;
    }
}
