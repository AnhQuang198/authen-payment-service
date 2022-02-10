package com.example.authenpaymentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Integer statusCode;
    private Date timestamp;
    private String message;
    private String path;
}
