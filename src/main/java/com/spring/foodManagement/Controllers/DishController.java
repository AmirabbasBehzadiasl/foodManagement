package com.spring.foodManagement.Controllers;

import com.spring.foodManagement.Dtos.DishCreateDto;
import com.spring.foodManagement.Dtos.DishResponseDto;
import com.spring.foodManagement.Services.DishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/getAllDishes")
    public ResponseEntity<?> getAllDishes() {
        List<DishResponseDto> dishes = dishService.getAllDishes();
        if (dishes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No dishes found");
        }
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/getDishByName")
    public ResponseEntity<?> getDishByName(@RequestParam String name) {
        return ResponseEntity.ok(dishService.getDishByName(name));
    }

    @PostMapping("/addDish")
    public ResponseEntity<?> addDish(@Valid @RequestBody DishCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.addDish(dto));
    }

    @PutMapping("/updateDishByName")
    public ResponseEntity<?> updateDishByName(@RequestParam String name, @Valid @RequestBody DishCreateDto dto) {
        return ResponseEntity.ok(dishService.updateDishByName(name, dto));
    }

    @DeleteMapping("/deleteDishByName")
    public ResponseEntity<?> deleteDishByName(@RequestParam String name) {
        dishService.deleteDishByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Dish with name " + name + " deleted successfully");
    }
}
