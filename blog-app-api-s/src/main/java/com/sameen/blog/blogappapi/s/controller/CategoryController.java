package com.sameen.blog.blogappapi.s.controller;

import com.sameen.blog.blogappapi.s.payloads.ApiResponse;
import com.sameen.blog.blogappapi.s.payloads.CategoryDto;
import com.sameen.blog.blogappapi.s.services.CategoryService;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        log.info("==> CategoryController :: Inside createCategory() <==");

        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable("categoryId") Integer categoryId, @RequestBody CategoryDto categoryDto){
        log.info("==> CategoryController :: Inside updateCategory() <==");

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        System.out.println("Category has been updated");
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
        log.info("==> CategoryController :: Inside deleteCategory() <==");

        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Integer categoryId){
        log.info("==> CategoryController :: Inside getCategoryById() <==");

        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        log.info("==> CategoryController :: Inside getAllCategory() <==");

        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

}
