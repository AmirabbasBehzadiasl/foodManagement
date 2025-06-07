package com.spring.foodManagement.Services;

import com.spring.foodManagement.Dtos.DishCreateDto;
import com.spring.foodManagement.Dtos.DishResponseDto;
import com.spring.foodManagement.Exceptions.AlreadyExistException;
import com.spring.foodManagement.Exceptions.NotFoundException;
import com.spring.foodManagement.Mapper.DishMapper;
import com.spring.foodManagement.Models.Dish;
import com.spring.foodManagement.Repositories.CategoryRepository;
import com.spring.foodManagement.Repositories.DishRepository;
import com.spring.foodManagement.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public DishService(DishRepository dishRepository, DishMapper dishMapper,
                       CategoryRepository categoryRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<DishResponseDto> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(dishMapper::toDto)
                .toList();
    }

    public DishResponseDto getDishByName(String name) {
        Dish dish = dishRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("No dish found with name: " + name));
        return dishMapper.toDto(dish);
    }

    public DishResponseDto addDish(DishCreateDto dto) {
        dishRepository.findByName(dto.getName())
                .ifPresent(existing -> {
                    throw new AlreadyExistException("Dish with name " + dto.getName() + " already exists");
                });

        Dish dish = dishMapper.toModel(dto);
        dish.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategoryId())));
        dish.setChef(userRepository.findById(dto.getChefId())
                .orElseThrow(() -> new NotFoundException("Chef not found with ID: " + dto.getChefId())));

        return dishMapper.toDto(dishRepository.save(dish));
    }

    public DishResponseDto updateDishByName(String name, DishCreateDto dto) {
        Dish existingDish = dishRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Dish with name " + name + " not found"));

        dishMapper.updateFromDto(dto, existingDish);
        existingDish.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategoryId())));
        existingDish.setChef(userRepository.findById(dto.getChefId())
                .orElseThrow(() -> new NotFoundException("Chef not found with ID: " + dto.getChefId())));

        return dishMapper.toDto(dishRepository.save(existingDish));

    }

    public void deleteDishByName(String name) {
        Dish dish = dishRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Dish with name " + name + " not found"));
        dishRepository.delete(dish);
    }
}
