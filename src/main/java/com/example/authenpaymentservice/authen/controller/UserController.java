package com.example.authenpaymentservice.authen.controller;

import com.example.authenpaymentservice.authen.model.request.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends BaseController{
    private static final String TOKEN_TYPE = "x-auth-token";

    @GetMapping("/me")
    public ResponseEntity<?> getUser(
            @RequestHeader(TOKEN_TYPE) String token
    ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return userService.getUser(userId);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(
            @RequestHeader(TOKEN_TYPE) String token,
            @RequestBody UpdateProfileRequest request
            ) {
        int userId = tokenProvider.getUserIdFromJWT(token);
        return userService.updateProfile(request, userId);
    }

}
