package com.movieflix.controller;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories () {
        return categoryService.findAll();
    }

    @PostMapping
    public CategoryResponse saveCategory (@RequestBody CategoryRequest request) {
        return categoryService.saveCategory(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse getByCategoryId (@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteByCategoryId (@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}