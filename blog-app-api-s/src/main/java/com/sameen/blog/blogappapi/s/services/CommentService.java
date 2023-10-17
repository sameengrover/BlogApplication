package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.payloads.CommentDto;

public interface CommentService {

    CommentDto addComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);
}
