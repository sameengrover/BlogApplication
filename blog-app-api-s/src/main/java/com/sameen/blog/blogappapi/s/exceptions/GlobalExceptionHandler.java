package com.sameen.blog.blogappapi.s.exceptions;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
//Global exception class that handles the all controller Api's
public class GlobalExceptionHandler extends RuntimeException {

    public GlobalExceptionHandler(String message) {
        super(message);
    }

    public GlobalExceptionHandler(String message, Throwable throwable) {
        super(message, throwable);
    }



}
