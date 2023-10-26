package com.sameen.blog.blogappapi.s.security;

import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServices implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Log the username being authenticated
            log.info("Authenticating user: " + username);

            User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user", "email " + username, 0));

            // Log the user details retrieved
            log.info("User details: " + user.getUsername());

            return user;
        } catch (Exception ex) {
            // Log any exceptions for troubleshooting
            log.error("Error during authentication: " + ex.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
    }
}
