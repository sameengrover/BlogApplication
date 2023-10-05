package com.sameen.blog.blogappapi.s.payloads;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Data
@Validated
public class UserDto {
    private int id;
//    @NotNull(message = "name can't be null")
    @NotEmpty(message = "Name can't be empty")
    @Size(min =4, message = "Username must be min of 4 chars.")
    private String name;

    @Email(message = "Please enter valid email address")
    private String email;

    @NotEmpty
    @Size(min =3, max =10, message = "Password must be of atleast 3 chars and not more than 10 chars")
//    @Pattern(regexp = )    --> get pswrd pattern from google and paste here to set the password
    private String password;

    @NotEmpty(message = "About can't be Empty")
    private String about;
}
