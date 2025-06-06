package com.spring.foodManagement.Mapper;


import com.spring.foodManagement.Dtos.*;
import com.spring.foodManagement.Models.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toModel(CategoryCreateDto dto);

    CategoryResponseDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CategoryCreateDto createDto,@MappingTarget Category category);
}
