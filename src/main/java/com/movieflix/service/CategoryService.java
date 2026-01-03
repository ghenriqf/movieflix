package com.movieflix.service;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.entity.Category;
import com.movieflix.mapper.CategoryMapper;
import com.movieflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll () {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse saveCategory (CategoryRequest request) {
        Category entity = CategoryMapper.toEntity(request);
        categoryRepository.save(entity);
        return CategoryMapper.toResponse(entity);
    }

    public CategoryResponse findById (Long id) {
        Optional<Category> entity = categoryRepository.findById(id);

        if (entity.isPresent()) {
            return CategoryMapper.toResponse(entity.get());
        }

        return null;
    }

    public void deleteCategory (Long id) {
        categoryRepository.deleteById(id);
    }
}
