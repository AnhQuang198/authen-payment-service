package com.example.authenpaymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAccessException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public NoAccessException(String exception) {
        super(exception);
    }
}
