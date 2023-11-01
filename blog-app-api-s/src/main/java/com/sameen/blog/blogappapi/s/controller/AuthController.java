package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.entities.Token;
import com.sameen.blog.blogappapi.s.entities.TokenType;
import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.BadCredentialException;
import com.sameen.blog.blogappapi.s.exceptions.GlobalExceptionHandler;
import com.sameen.blog.blogappapi.s.payloads.JwtAuthRequest;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.repositories.TokenRepo;
import com.sameen.blog.blogappapi.s.security.JwtAuthResponse;
import com.sameen.blog.blogappapi.s.security.JwtTokenHelper;
import com.sameen.blog.blogappapi.s.security.UserDetailsServices;
import com.sameen.blog.blogappapi.s.services.impl.AuthImpl;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthImpl authImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
         JwtAuthResponse response = authImpl.createToken(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser = this.authImpl.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

}

