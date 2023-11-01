package com.sameen.blog.blogappapi.s.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Blog Application",
                version ="1.0.0",
                description = "This is the blog application where we can add the user and post as per the requirements",
                termsOfService = "runcodenow",
                contact = @Contact(
                        name ="Sameen",
                        email = "sameengrover12@gmail.com"
                ),
                license = @License(
                        name ="License",
                        url ="LicenseURL"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9090"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://codewithtech.com/blog"
                )
        },
        security = {
                @SecurityRequirement(
                        name ="bearerAuth"
                )
        }
)
@SecurityScheme(
        name ="bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
