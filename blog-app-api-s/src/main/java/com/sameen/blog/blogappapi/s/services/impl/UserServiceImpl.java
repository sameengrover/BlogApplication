package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.config.AppConstants;
import com.sameen.blog.blogappapi.s.entities.Role;
import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.payloads.UserDto;
import com.sameen.blog.blogappapi.s.repositories.RoleRepo;
import com.sameen.blog.blogappapi.s.repositories.UserRepo;
import com.sameen.blog.blogappapi.s.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.matcher.StringMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto){
//  We have to save the user object in userRepo, we created the dtoToUser and userToDto
        log.info("==> ServiceImpl :: Inside createUser() <==");
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user); // saving the user into User entity

        return this.userToDto(savedUser);
    }
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        log.info("==> ServiceImpl :: Inside updateUser() <==");

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

         user.setName(userDto.getName());
         user.setEmail(userDto.getEmail());
         user.setAbout(userDto.getAbout());
         user.setPassword(user.getPassword());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser); //Converting user to userDto

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        log.info("==> ServiceImpl :: Inside getUserById() <==");

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("==> ServiceImpl :: Inside getAllUser() <==");

        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtoList = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        log.info("==> ServiceImpl :: Inside deleteUser() <==");

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

//    These below methods are for converting the one object to other
    public User dtoToUser(UserDto userDto){
        User user = new ModelMapper().map(userDto,User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());

        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto = new ModelMapper().map(user, UserDto.class);

//        userDto.setEmail(user.getEmail());
//        userDto.setAbout(user.getAbout());
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setPassword(user.getPassword());

        return userDto;
    }
}
