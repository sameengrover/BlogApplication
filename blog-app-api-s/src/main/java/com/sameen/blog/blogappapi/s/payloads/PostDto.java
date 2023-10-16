package com.sameen.blog.blogappapi.s.payloads;

import com.sameen.blog.blogappapi.s.entities.Category;
import com.sameen.blog.blogappapi.s.entities.Comment;
import com.sameen.blog.blogappapi.s.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private UserDto user;
    private CategoryDto category;

    private Set<Comment> comments = new HashSet<>();

}
