package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.CommentDto;
import com.sameen.blog.blogappapi.s.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/addComments/{postId}")
    public ResponseEntity<CommentDto> addComments(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        log.info("==> CommentController : Inside addComments <==");
        CommentDto commentDto1 = commentService.addComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
     log.info("==> CommentController : Inside deleteComment <==");
     this.commentService.deleteComment(commentId);
     return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Succesfully", true), HttpStatus.OK);

    }
}
