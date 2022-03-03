package com.example.authenpaymentservice.upload.model;

import lombok.Data;

@Data
public class UploadResponse {
    private String filePath;
    private Integer statusCode;
}
