package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.*;
import com.spring.foodManagement.Models.Review;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toModel(ReviewCreateDto dto);

    @Mapping(source = "user.userName" , target = "userName")
    @Mapping(source = "dish.name" , target = "dishName")
    ReviewResponseDto toDto(Review review);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(ReviewCreateDto dto , @MappingTarget Review review);
}

