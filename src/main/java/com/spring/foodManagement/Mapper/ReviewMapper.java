package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.*;
import com.spring.foodManagement.Models.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toModel(ReviewCreateDto dto);

    ReviewResponseDto toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Review review, ReviewCreateDto dto);
}

