package com.example.authenpaymentservice.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/upload")
public class UploadController extends BaseController{
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestHeader("x-auth-token") String token,
            @RequestParam(value = "file") MultipartFile fileUpload
    ) {
        return storageService.uploadImage(fileUpload);
    }

    @PostMapping("/excel")
    public ResponseEntity<?> uploadExcel(
            @RequestHeader("x-auth-token") String token,
            @RequestParam(value = "file") MultipartFile fileUpload
    ) {
        return excelProcessService.uploadExcel(fileUpload);
    }

}
