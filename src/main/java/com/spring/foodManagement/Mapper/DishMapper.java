package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.DishCreateDto;
import com.spring.foodManagement.Dtos.DishResponseDto;
import com.spring.foodManagement.Models.Category;
import com.spring.foodManagement.Models.Dish;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "chef", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Dish toModel(DishCreateDto dto);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "chefName", source = "chef.name")
    DishResponseDto toDto(Dish dish);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "chef", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateFromDto(DishCreateDto dto, @MappingTarget Dish dish);

}
