package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
    void deleteUser(Integer userId);

}
