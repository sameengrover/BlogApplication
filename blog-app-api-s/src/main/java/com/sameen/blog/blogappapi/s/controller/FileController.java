package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.config.FileUploadProperties;
import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import com.sameen.blog.blogappapi.s.services.FileService;
import com.sameen.blog.blogappapi.s.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    PostService postService;

    @Autowired
    FileUploadProperties uploadProperties;

    @PostMapping("/post/image/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("file")MultipartFile file,
            @PathVariable("postId") Integer postId) throws IOException{
        PostDto postDto =  this.postService.getPostById(postId);
        String fileName = this.fileService.uploadPostImage(uploadProperties.getDir(), file);
//     This will be done by updatePost Api, have to create the api under PostService
        postDto.setImageName(fileName);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }



}
