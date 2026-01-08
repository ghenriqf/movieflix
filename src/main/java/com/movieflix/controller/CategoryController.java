package com.movieflix.controller;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll () {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save (@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById (@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}