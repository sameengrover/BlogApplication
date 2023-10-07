package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    Post updatePost(PostDto postDto);

    void deletePost(Integer postId);

//    Get All Post
    List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);

//    Get All post By Id
    PostDto getPostById(Integer postId);

//    Get All Post By Category
    List<PostDto> getPostsByCategory(Integer categoryId);

//    Get All Post By User
    List<PostDto> getPostsByUser(Integer userId);

//    Search Posts
    List<Post> searchPosts(String keyword);

}
