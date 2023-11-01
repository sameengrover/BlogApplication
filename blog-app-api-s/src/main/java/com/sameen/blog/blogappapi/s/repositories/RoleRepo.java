package com.sameen.blog.blogappapi.s.repositories;

import com.sameen.blog.blogappapi.s.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
