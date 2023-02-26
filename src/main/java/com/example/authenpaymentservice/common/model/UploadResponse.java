package com.example.authenpaymentservice.common.model;

import lombok.Data;

@Data
public class UploadResponse {
    private String filePath;
    private Integer statusCode;
}
