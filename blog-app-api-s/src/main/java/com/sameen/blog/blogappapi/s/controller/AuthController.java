package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.entities.Token;
import com.sameen.blog.blogappapi.s.entities.TokenType;
import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.BadCredentialException;
import com.sameen.blog.blogappapi.s.exceptions.GlobalExceptionHandler;
import com.sameen.blog.blogappapi.s.payloads.JwtAuthRequest;
import com.sameen.blog.blogappapi.s.repositories.TokenRepo;
import com.sameen.blog.blogappapi.s.security.JwtAuthResponse;
import com.sameen.blog.blogappapi.s.security.JwtTokenHelper;
import com.sameen.blog.blogappapi.s.security.UserDetailsServices;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private AuthenticationManager authenicationManager;
    @Autowired
    private UserDetailsServices userDetailsServices;
    @Autowired
    private BadCredentialException badCredentialException;

    @Autowired
    private TokenRepo tokenRepo;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
        log.info("==> AuthController :: Inside createToken <==");
        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsServices.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        Token token1 = new Token();
        token1.setToken(token);
        token1.setTokenType(TokenType.BEARER);
        token1.setExpired(false);
        token1.setRevoked(false);
        token1.setUser((User)userDetails);

        revokeAllTokens((User)userDetails);

        tokenRepo.save(token1);

        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenicationManager.authenticate(authenticationToken);

        }
        catch(BadCredentialsException ex) {
//            System.out.println("Password doesn't match");
//            throw new Exception("Invalid Details!!");
                throw new GlobalExceptionHandler("Invalid Details!!");

        }
    }

    private void revokeAllTokens(User user){
        var validUserToken = tokenRepo.findAllValidTokensByUser(user.getId());
        if(validUserToken.isEmpty()){
            log.info("No Token found for now");
            return;
        }

        validUserToken.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        }
                );

    }
}
