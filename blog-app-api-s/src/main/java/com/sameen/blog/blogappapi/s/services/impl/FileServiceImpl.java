package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.config.FileUploadProperties;
import com.sameen.blog.blogappapi.s.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class FileServiceImpl implements FileService{
    @Autowired
    FileUploadProperties fileUploadProperties;

    @Override
    public boolean uploadFile( MultipartFile file) {
        try {
            log.info("==> FileServiceImpl :: Inside uploadFile() <==");
            String uploadDir = fileUploadProperties.getDir();

            File f = new File(uploadDir);

            if (!f.exists()) {
                f.mkdirs();
            }

            String filePath = uploadDir + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            return true;
    }catch (IOException e) {
        e.printStackTrace();
        return false;
        }
    }
}
