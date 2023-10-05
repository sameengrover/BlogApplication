package com.sameen.blog.blogappapi.s.exceptions;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
//Global exception class that handles the all controller Api's
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
            String message = ex.getMessage();
            ApiResponse apiResponse = new ApiResponse(message, false);
            return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
        }


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
           Map<String, String> resp = new HashMap<>();
           ex.getBindingResult().getAllErrors().forEach((error)->{
               String fieldName = ((FieldError) error).getField();
               String message = error.getDefaultMessage();
               resp.put(fieldName, message);
           });

           return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
        }
}
