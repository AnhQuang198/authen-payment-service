package com.example.authenpaymentservice.upload.controller;

import com.example.authenpaymentservice.upload.service.ExcelProcessService;
import com.example.authenpaymentservice.upload.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/upload")
public class UploadController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private ExcelProcessService excelProcessService;

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
