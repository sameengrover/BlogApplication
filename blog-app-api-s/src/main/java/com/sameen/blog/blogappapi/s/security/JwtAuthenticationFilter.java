package com.sameen.blog.blogappapi.s.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameen.blog.blogappapi.s.repositories.TokenRepo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    private final TokenRepo tokenRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("==> JwtAuthenticationFilter :: Inside doFilerInternal <==");
        String requestToken = request.getHeader("Authorization");

        System.out.println(requestToken);

        final String userEmail;

        final String token;

        if (requestToken == null || !requestToken.startsWith("Bearer ")) { // Token is starting from: Bearer 2353454sdg
            filterChain.doFilter(request, response);
            return;
        }

        token = requestToken.substring(7);
        try {
            userEmail = jwtTokenHelper.getUsernameFromToken(token);
        } catch (ExpiredJwtException ex) {
            log.error("JWT Token has expired.");
            log.trace("Expired JWT token trace: " + ex);
            handleJwtException(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            return;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: " + ex);
            handleJwtException(response, HttpServletResponse.SC_BAD_REQUEST, "Unsupported JWT Token");
            return;
        } catch (MalformedJwtException ex) {
            log.error("Malformed JWT Token.");
            log.trace("Invalid JWT token trace: " + ex);
            handleJwtException(response, HttpServletResponse.SC_BAD_REQUEST, "Malformed JWT Token");
            return;
        } catch (IllegalArgumentException ex) {
            log.error("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: " + ex);
            handleJwtException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
            return;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: " + ex);
            handleJwtException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token Signature");
            return;
        }

//        Once we get the token, now validate the token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var isTokenValid = tokenRepo.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (Boolean.TRUE.equals(jwtTokenHelper.validateToken(token, userDetails)) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                log.error("Invalid JWT Token, Validation Fails!!");
                handleJwtException(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token, Validation Fails!!");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleJwtException(HttpServletResponse response, int status, String errorMessage) throws IOException {

        response.setStatus(status);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "FAILURE");
        errorResponse.put("errorMessage", errorMessage);
        errorResponse.put("statusCode", String.valueOf(status));
        errorResponse.put("responseType", "E");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);

    }
}