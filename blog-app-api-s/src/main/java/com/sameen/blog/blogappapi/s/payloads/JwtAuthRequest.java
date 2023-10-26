package com.sameen.blog.blogappapi.s.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    String username;
    String password;
}
