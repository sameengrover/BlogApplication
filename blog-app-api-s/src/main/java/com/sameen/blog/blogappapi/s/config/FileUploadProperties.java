package com.sameen.blog.blogappapi.s.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "upload")
public class FileUploadProperties {
    private String dir;
}
