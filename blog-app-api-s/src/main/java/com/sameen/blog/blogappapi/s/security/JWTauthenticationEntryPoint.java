package com.sameen.blog.blogappapi.s.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTauthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("==> JWTauthenticationEntryPoint :: Inside commence <==");
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!!");

        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String message = "Access Denied";

        Map<String,Object> errorAttributes = new HashMap<String,Object>();
        errorAttributes.put("status", status);
        errorAttributes.put("message", message);

        response.setStatus(status);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), errorAttributes);

    }

}
