package com.sameen.blog.blogappapi.s.services;

import com.sameen.blog.blogappapi.s.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
//    Create Category
    CategoryDto createCategory(CategoryDto categoryDto);

//    Update Category
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

//    Delete Category
    void deleteCategory(Integer categoryId);

//    Get Category By ID
    CategoryDto getCategoryById(Integer categoryId);

//    Get All Categories
    List<CategoryDto> getAllCategories();

}
