package com.sameen.blog.blogappapi.s.payloads;

import com.sameen.blog.blogappapi.s.entities.Category;
import com.sameen.blog.blogappapi.s.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private UserDto user;
    private CategoryDto category;
}
