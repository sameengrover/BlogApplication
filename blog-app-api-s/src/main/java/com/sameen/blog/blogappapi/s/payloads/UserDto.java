package com.sameen.blog.blogappapi.s.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@Data
public class UserDto {
    private int id;

    @NotNull
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private String about;
}
