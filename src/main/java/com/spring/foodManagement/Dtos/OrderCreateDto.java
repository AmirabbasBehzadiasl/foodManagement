package com.spring.foodManagement.Dtos;

import com.spring.foodManagement.Enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderCreateDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Status is required")
    private OrderStatus status;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemCreateDto> items;


    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public List<OrderItemCreateDto> getItems() { return items; }
    public void setItems(List<OrderItemCreateDto> items) { this.items = items; }
}