package com.sameen.blog.blogappapi.s.config;

import com.sameen.blog.blogappapi.s.security.JWTauthenticationEntryPoint;
import com.sameen.blog.blogappapi.s.security.JwtAuthenticationFilter;
import com.sameen.blog.blogappapi.s.security.UserDetailsServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.AccessType;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

import java.security.SecureRandom;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserDetailsServices userDetailsServices;

    private final JWTauthenticationEntryPoint jwTauthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    private final User user;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("===: SecurityConfig:: Inside filterChain Method:===");

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(SecurityConfigConstants.PUBLIC_URL).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwTauthenticationEntryPoint));

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }

//    UserDetailsService and passwordEncoder is already written in AuthenticationManager,
//    so we don't need to write it. We can simply use authenticationManager to fulfill the requirements
@Bean
public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("===: SecurityConfig:: Inside getPasswordEncoder Method:===");
        // Use the salt in the BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        log.info("===: SecurityConfig:: Inside daoAuthenticationProvider Method:===");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsServices);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
