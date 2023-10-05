package com.sameen.blog.blogappapi.s.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
//@Validated
public class CategoryDto {

    private int categoryId;
    @NotEmpty(message = "Title can't be empty")
    private String categoryTitle;
    @NotEmpty(message = "No empty description allowed")
    private String categoryDescription;


}
