package com.example.authenpaymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
                        HttpStatus.NOT_FOUND.value(),
                        new Date(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException (
            BadRequestException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
                        HttpStatus.BAD_REQUEST.value(),
                        new Date(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
                        HttpStatus.UNAUTHORIZED.value(),
                        new Date(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<?> noAccessException(NoAccessException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
                        HttpStatus.FORBIDDEN.value(),
                        new Date(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        new Date(),
                        ex.getMessage(),
                        ((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
