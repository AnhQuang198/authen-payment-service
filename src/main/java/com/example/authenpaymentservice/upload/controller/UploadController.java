package com.example.authenpaymentservice.upload.controller;

import com.example.authenpaymentservice.upload.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class UploadController {
    @Autowired private StorageService storageService;

    @PostMapping("upload")
    public ResponseEntity<?> upload(
            @RequestHeader("x-auth-token") String token,
            @RequestParam(value = "file") MultipartFile fileUpload
    ) {
        return storageService.uploadImage(fileUpload);
    }
}
