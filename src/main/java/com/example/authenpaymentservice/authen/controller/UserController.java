package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.dtos.UpdateProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(value = "*", maxAge = 3600L)
public class UserController extends BaseController{

    @GetMapping("/me")
    public ResponseEntity<?> getUser(@RequestHeader("x-user-id") Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("x-user-id") Integer userId,
            @RequestBody UpdateProfileDTO dto
            ) {
        return userService.updateProfile(dto, userId);
    }

}
