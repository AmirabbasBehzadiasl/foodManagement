package com.spring.foodManagement.Services;

import com.spring.foodManagement.Dtos.CategoryCreateDto;
import com.spring.foodManagement.Dtos.CategoryResponseDto;
import com.spring.foodManagement.Exceptions.AlreadyExistException;
import com.spring.foodManagement.Exceptions.NotFoundException;
import com.spring.foodManagement.Mapper.CategoryMapper;
import com.spring.foodManagement.Models.Category;
import com.spring.foodManagement.Repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDto getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category with name " + name + " not found"));
        return categoryMapper.toDto(category);
    }

    public CategoryResponseDto addCategory(CategoryCreateDto dto) {
        categoryRepository.findByName(dto.getName())
                .ifPresent(c -> {
                    throw new AlreadyExistException("Category with name " + dto.getName() + " already exists");
                });
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(dto)));
    }

    public CategoryResponseDto updateCategoryByName(String name, CategoryCreateDto dto) {
        Category existingCategory = categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category with name " + name + " not found"));

        categoryMapper.update(dto, existingCategory);
        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    public void deleteCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category with name " + name + " not found"));
        categoryRepository.delete(category);
    }
}
