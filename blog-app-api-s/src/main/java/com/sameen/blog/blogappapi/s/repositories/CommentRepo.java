package com.sameen.blog.blogappapi.s.repositories;

import com.sameen.blog.blogappapi.s.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer>{


}
