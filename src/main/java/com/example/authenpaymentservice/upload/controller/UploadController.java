package com.example.authenpaymentservice.upload.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class UploadController {
    @PostMapping("upload")
    public ResponseEntity<?> upload(
            @RequestPart(value = "file") MultipartFile file
    ) {
        return null;
    }
}
