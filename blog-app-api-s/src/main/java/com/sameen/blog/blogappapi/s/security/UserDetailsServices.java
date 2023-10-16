package com.sameen.blog.blogappapi.s.security;

import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =   this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user", "email " +username, 0));

    return user;

    }
}
