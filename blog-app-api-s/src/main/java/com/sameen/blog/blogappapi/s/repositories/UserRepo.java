package com.sameen.blog.blogappapi.s.repositories;

import com.sameen.blog.blogappapi.s.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>{

}
