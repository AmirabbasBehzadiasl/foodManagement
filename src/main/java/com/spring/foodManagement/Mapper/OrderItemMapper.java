package com.spring.foodManagement.Mapper;

import com.spring.foodManagement.Dtos.OrderItemCreateDto;
import com.spring.foodManagement.Dtos.OrderItemResponseDto;
import com.spring.foodManagement.Models.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "dish", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toModel(OrderItemCreateDto dto);

    @Mapping(source = "dish.id", target = "dishId")
    @Mapping(source = "dish.price" , target = "price")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(OrderItemCreateDto dto, @MappingTarget OrderItem entity);
}
