package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.dtos.UpdateProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends BaseController{

    @GetMapping("/me")
    public ResponseEntity<?> getUser(
            @RequestHeader("x-auth-token") String token
    ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return userService.getUser(userId);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("x-auth-token") String token,
            @RequestBody UpdateProfileDTO dto
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return userService.updateProfile(dto, userId);
    }

}
