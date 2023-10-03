package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.services.UserService;
import com.sameen.blog.blogappapi.s.services.impl.UserServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class userController {
    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        System.out.println("User has been deleted");
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
            this.userService.deleteUser(userId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }
    @GetMapping("/getUserById/{userId}")
        public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) {
            UserDto user1 = this.userService.getUserById(userId);
            return ResponseEntity.ok(user1);
    }
      @GetMapping("/GetAllUsers")
      public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
      }
    }
