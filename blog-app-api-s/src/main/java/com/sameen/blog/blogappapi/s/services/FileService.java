package com.sameen.blog.blogappapi.s.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadPostImage(String path, MultipartFile file) throws IOException;
}
