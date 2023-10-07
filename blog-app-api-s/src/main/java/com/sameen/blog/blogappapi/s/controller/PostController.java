package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.entities.Category;
import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import com.sameen.blog.blogappapi.s.services.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
        public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
        @PathVariable Integer userId,
        @PathVariable Integer categoryId)
    {
        log.info("==> PostController :: createPost() <==");

        PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
        }

    @GetMapping("/user/{userId}/posts")
        public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        log.info("==> PostController :: Inside getPostsByUser() <==");

        List <PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
        }

    @GetMapping("/category/{categoryId}/posts")
        public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        log.info("==> PostController :: Inside getPostsByCategory() <==");

        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
        }

   @GetMapping("/getAllPosts")
        public ResponseEntity<List<PostDto>> getAllPost(
                @RequestParam(value = "pageNumber" , defaultValue = "1", required = false) Integer pageNumber,
                @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize ){
        log.info("==> PostController :: Inside getAllPost() <==");

        List<PostDto> allPosts = this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
        }
   @GetMapping("/posts/{postId}")
        public ResponseEntity<PostDto> getSinglePostById(@PathVariable Integer postId){
        log.info("==> PostController :: Inside getSinglePostById <==");

        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
        }
}