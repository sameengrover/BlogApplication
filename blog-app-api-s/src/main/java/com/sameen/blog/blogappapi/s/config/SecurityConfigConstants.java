package com.sameen.blog.blogappapi.s.config;

public class SecurityConfigConstants {

    /*----Adding private constructor to hide the implicit public one ----*/
    private SecurityConfigConstants() {}

    public static final String[] PUBLIC_URL = {
            "/api/v1/auth/**",

    };
}