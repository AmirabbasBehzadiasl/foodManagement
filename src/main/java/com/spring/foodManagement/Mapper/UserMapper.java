package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.UserCreateDto;
import com.spring.foodManagement.Dtos.UserResponseDto;
import com.spring.foodManagement.Models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    User toModel(UserCreateDto dto);

    UserResponseDto toDto(User user);

    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserCreateDto dto, @MappingTarget User user);
}
