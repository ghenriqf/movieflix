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

    public CategoryResponse save (CategoryRequest request) {
        Category entity = CategoryMapper.toEntity(request);
        Category entitySaved = categoryRepository.save(entity);
        return CategoryMapper.toResponse(entitySaved);
    }

    public CategoryResponse findById (Long id) throws Exception {
        return categoryRepository
                .findById(id)
                .map(CategoryMapper::toResponse)
                .orElseThrow(Exception::new);
    }

    public void deleteById (Long id) {
        categoryRepository.deleteById(id);
    }
}
