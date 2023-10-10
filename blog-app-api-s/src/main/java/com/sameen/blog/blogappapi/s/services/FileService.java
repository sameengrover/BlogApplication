package com.sameen.blog.blogappapi.s.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    boolean uploadFile(MultipartFile file) throws IOException;
}
