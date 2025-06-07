package com.spring.foodManagement.Controllers;

import com.spring.foodManagement.Dtos.CategoryCreateDto;
import com.spring.foodManagement.Dtos.CategoryResponseDto;
import com.spring.foodManagement.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found");
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getCategoryByName")
    public ResponseEntity<?> getCategoryByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(dto));
    }

    @PutMapping("/updateCategoryByName")
    public ResponseEntity<?> updateCategoryByName(@RequestParam String name, @Valid @RequestBody CategoryCreateDto dto) {
        return ResponseEntity.ok(categoryService.updateCategoryByName(name, dto));
    }

    @DeleteMapping("/deleteCategoryByName")
    public ResponseEntity<?> deleteCategoryByName(@RequestParam String name) {
        categoryService.deleteCategoryByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category deleted successfully");
    }
}
