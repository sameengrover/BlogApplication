package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.config.AppConstants;
import com.sameen.blog.blogappapi.s.entities.Role;
import com.sameen.blog.blogappapi.s.entities.Token;
import com.sameen.blog.blogappapi.s.entities.TokenType;
import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.GlobalExceptionHandler;
import com.sameen.blog.blogappapi.s.payloads.JwtAuthRequest;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.repositories.RoleRepo;
import com.sameen.blog.blogappapi.s.repositories.TokenRepo;
import com.sameen.blog.blogappapi.s.repositories.UserRepo;
import com.sameen.blog.blogappapi.s.security.JwtAuthResponse;
import com.sameen.blog.blogappapi.s.security.JwtTokenHelper;
import com.sameen.blog.blogappapi.s.security.UserDetailsServices;
import com.sameen.blog.blogappapi.s.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthImpl implements AuthService {

    private final TokenRepo tokenRepo;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServices userDetailsServices;
    private final JwtTokenHelper jwtHelper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public JwtAuthResponse createToken(String username, String password) throws Exception {
        authenticate(username, password);
        UserDetails userDetails = this.userDetailsServices.loadUserByUsername(username);
        String token = this.jwtHelper.generateToken(userDetails);

        JwtAuthResponse response =   new JwtAuthResponse();
        response.setToken(token);

        Token token2 = new Token();
        token2.setToken(token);
        token2.setTokenType(TokenType.BEARER);
        token2.setExpired(false);
        token2.setRevoked(false);
        token2.setUser((User)userDetails);

        revokeAllTokens((User)userDetails);

        tokenRepo.save(token2);
        return response;
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
        private void authenticate(String username, String password) throws Exception {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            try {
                authenticationManager.authenticate(authenticationToken);
            } catch (BadCredentialsException ex) {
                throw new GlobalExceptionHandler("Invalid Details!!");
            }
        }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User saved_user = userRepo.save(user);

        return this.modelMapper.map(saved_user, UserDto.class);
    }

}
