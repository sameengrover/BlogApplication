package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        log.info("==> UserController :: createUser() <==");
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        log.info("==> UserController :: updateUser() <==");

        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        System.out.println("User has been updated");
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        log.info("==> UserController :: deleteUser() <==");

        this.userService.deleteUser(userId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }
    @GetMapping("/getUserById/{userId}")
        public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) {
        log.info("==> UserController :: getUserById() <==");

        UserDto user1 = this.userService.getUserById(userId);
            return ResponseEntity.ok(user1);
    }
      @GetMapping("/GetAllUsers")
      public ResponseEntity<List<UserDto>> getAllUser(){
          log.info("==> UserController :: getAllUser() <==");

          return ResponseEntity.ok(this.userService.getAllUser());
      }
    }
