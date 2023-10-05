package com.sameen.blog.blogappapi.s.repositories;

import com.sameen.blog.blogappapi.s.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
