package com.example.authenpaymentservice.authen.service;

import com.example.authenpaymentservice.authen.dtos.UpdateProfileDTO;
import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
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
        User user = userRepository.findUserById(userId);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException(Message.NOT_FOUND);
        }
        user.setName(dto.getName());
        user.setAvatarUrl(dto.getAvatarUrl());
        user.setCoverUrl(dto.getCoverUrl());
        user.setDob(dto.getDob());
        user.setGender(dto.getGender());
        userRepository.save(user);
        return ResponseEntity.ok("Updated");
    }
}
