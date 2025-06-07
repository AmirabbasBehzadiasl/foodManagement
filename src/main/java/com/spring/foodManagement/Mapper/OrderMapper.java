package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.OrderCreateDto;
import com.spring.foodManagement.Dtos.OrderResponseDto;
import com.spring.foodManagement.Models.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toModel(OrderCreateDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "items", target = "items")
    OrderResponseDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateFromDto(OrderCreateDto dto, @MappingTarget Order order);
}
