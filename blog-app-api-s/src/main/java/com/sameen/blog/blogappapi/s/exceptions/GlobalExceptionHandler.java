package com.sameen.blog.blogappapi.s.exceptions;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//Global exception class that handles the all controller Api's
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
            String message = ex.getMessage();
            ApiResponse apiResponse = new ApiResponse(message, false);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
        }
}
