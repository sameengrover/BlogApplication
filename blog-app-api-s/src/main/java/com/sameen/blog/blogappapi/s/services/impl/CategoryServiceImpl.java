package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.entities.Category;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.payloads.CategoryDto;
import com.sameen.blog.blogappapi.s.repositories.CategoryRepo;
import com.sameen.blog.blogappapi.s.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("==> ServiceImpl :: Inside createCategory() <==");
        Category category = this.dtoToCategory(categoryDto); // Also we can use --> this.modelMapper.map(categoryDto, Category.class)
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        log.info("==> ServiceImpl :: Inside updateCategory() <==");

        Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category category1 = this.categoryRepo.save(category);
        CategoryDto categoryDto1 = this.modelMapper.map(category1, CategoryDto.class);

        return categoryDto1;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        log.info("==> ServiceImpl :: Inside deleteCategory() <==");

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "id", categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        log.info("==> ServiceImpl :: Inside getCategoryById() <==");

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("categogry","id", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        log.info("==> ServiceImpl :: Inside getAllCategories() <==");

        List<Category> categoryList = this.categoryRepo.findAll();
        List <CategoryDto> categoryDtoList = categoryList.stream().map(category -> this.modelMapper.map(category , CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        Category category = new ModelMapper().map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = new ModelMapper().map(category, CategoryDto.class);
        return categoryDto;
    }

}
