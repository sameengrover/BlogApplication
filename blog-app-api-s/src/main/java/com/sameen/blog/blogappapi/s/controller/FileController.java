package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        boolean uploadSuccessful = fileService.uploadFile(file);
        if(uploadSuccessful){
            return ResponseEntity.ok("File uploaded successfully");
        }
        else{
            return ResponseEntity.status(500).body("File upload failed");
        }
    }
}
