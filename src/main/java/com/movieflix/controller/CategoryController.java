package com.movieflix.controller;

import com.movieflix.entity.Category;
import com.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories () {
        return categoryService.findAll();
    }

    @PostMapping
    public Category saveCategory (@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping("/{id}")
    public Category getByCategoryId (@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteByCategoryId (@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}