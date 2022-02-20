package com.example.authenpaymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class OAuth2AuthenticationProcessingException extends RuntimeException{
    public OAuth2AuthenticationProcessingException(String message) {
        super(message);
    }
}
