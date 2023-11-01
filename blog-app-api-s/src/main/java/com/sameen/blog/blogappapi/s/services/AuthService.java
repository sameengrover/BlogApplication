package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.entities.Token;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.security.JwtAuthResponse;

public interface AuthService {
    public JwtAuthResponse createToken(String username, String password) throws Exception;

    UserDto registerUser(UserDto userDto);

}
