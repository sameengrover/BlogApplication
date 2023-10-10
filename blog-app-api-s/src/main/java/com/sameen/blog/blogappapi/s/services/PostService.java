package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import com.sameen.blog.blogappapi.s.payloads.PostResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    Post updatePost(PostDto postDto);

    void deletePost(Integer postId);

//    Get All Post
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy , String sortDir);

//    Get All post By Id
    PostDto getPostById(Integer postId);

//    Get All Post By Category
    List<PostDto> getPostsByCategory(Integer categoryId);

//    Get All Post By User
    List<PostDto> getPostsByUser(Integer userId);

//    Search Posts
    List<PostDto> searchPosts(String keyword);

}
