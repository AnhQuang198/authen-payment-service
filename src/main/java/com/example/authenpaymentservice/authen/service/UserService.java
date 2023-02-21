package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.model.request.UpdateProfileRequest;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService extends BaseService{

    public ResponseEntity<?> getUser(Long userId) {
        User user = new User();
        if (Objects.nonNull(userId)) {
            user = userRepository.findUserById(userId);
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> updateProfile(UpdateProfileRequest request, Long userId) {
        User user = userRepository.findUserById(userId);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        user.setName(request.getName());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setDob(request.getDob());
        user.setGender(request.getGender());
        userRepository.save(user);
        return ResponseEntity.ok("Updated");
    }
}
