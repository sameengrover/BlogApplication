package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.config.FileUploadProperties;
import com.sameen.blog.blogappapi.s.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    FileUploadProperties fileUploadProperties;

    @Override
    public String uploadPostImage(String path, MultipartFile file) throws IOException {
            log.info("==> FileServiceImpl :: Inside uploadFile() <==");
            String uploadDir = fileUploadProperties.getDir();

            String randomID = UUID.randomUUID().toString();
            String fileName1 = randomID.concat(uploadDir.substring(uploadDir.lastIndexOf(".")));

            File f = new File(uploadDir);

            if (!f.exists()) {
                f.mkdirs();
            }

            String filePath = uploadDir + File.separator + fileName1;
//            file.transferTo(new File(filePath));

            Files.copy(file.getInputStream(), Paths.get(filePath));

            return fileName1;

        }
    }
