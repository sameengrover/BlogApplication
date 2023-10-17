package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.entities.Comment;
import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.payloads.CommentDto;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import com.sameen.blog.blogappapi.s.repositories.CommentRepo;
import com.sameen.blog.blogappapi.s.repositories.PostRepo;
import com.sameen.blog.blogappapi.s.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId) {
    log.info("==> CommentServiceImpl : Inside addComment <==");
    Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
    Comment comment = this.modelMapper.map(commentDto, Comment.class);
    comment.setPost(post);
    Comment savedComment = this.commentRepo.save(comment);
    return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
    log.info("==> CommentServiceImpl : Inside deleteComment <==");

    Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));
    this.commentRepo.delete(comment);   // Delete
    }
}
