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
//
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        ValidDetails validDetails = new ValidDetails();
//        Map<String, String> message = new HashMap<>();
//        if (ex instanceof MethodArgumentNotValidException) {
//            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//            for (FieldError fieldError : fieldErrors) {
//                message.put(fieldError.getField(), fieldError.getDefaultMessage());
//            }
//            validDetails.setMessage(message);
//        } else {
//            message.put("default", ex.getLocalizedMessage());
//            validDetails.setMessage(message);
//        }
//
//        validDetails.setTimestamp(new Date());
//        validDetails.setError("Not valid exception");
//        validDetails.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//
//        // return exceptionMessageObj;
//        return new ResponseEntity<>(validDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
