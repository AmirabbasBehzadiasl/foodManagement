package com.spring.foodManagement.Services;

import com.spring.foodManagement.Dtos.OrderCreateDto;
import com.spring.foodManagement.Dtos.OrderResponseDto;
import com.spring.foodManagement.Exceptions.NotFoundException;
import com.spring.foodManagement.Mapper.OrderItemMapper;
import com.spring.foodManagement.Mapper.OrderMapper;
import com.spring.foodManagement.Models.Dish;
import com.spring.foodManagement.Models.Order;
import com.spring.foodManagement.Models.OrderItem;
import com.spring.foodManagement.Models.User;
import com.spring.foodManagement.Repositories.DishRepository;
import com.spring.foodManagement.Repositories.OrderRepository;
import com.spring.foodManagement.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final DishRepository dishRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper , DishRepository dishRepository , OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.dishRepository = dishRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }

    public OrderResponseDto addOrder(OrderCreateDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));

        Order order = orderMapper.toModel(dto);
        order.setUser(user);

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
            Dish dish = dishRepository.findById(itemDto.getDishId())
                    .orElseThrow(() -> new NotFoundException("Dish not found with id: " + itemDto.getDishId()));

            OrderItem orderItem = orderItemMapper.toModel(itemDto);
            orderItem.setDish(dish);
            orderItem.setOrder(order);
            return orderItem;
        }).toList();

        order.setItems(items);

        return orderMapper.toDto(orderRepository.save(order));
    }

    public OrderResponseDto updateOrderById(Long id, OrderCreateDto dto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));

        if (!existingOrder.getUser().getId().equals(dto.getUserId())) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));
            existingOrder.setUser(user);
        }

        existingOrder.getItems().clear();

        List<OrderItem> updatedItems = dto.getItems().stream().map(itemDto -> {
            Dish dish = dishRepository.findById(itemDto.getDishId())
                    .orElseThrow(() -> new NotFoundException("Dish not found with id: " + itemDto.getDishId()));

            OrderItem orderItem = orderItemMapper.toModel(itemDto);
            orderItem.setDish(dish);
            orderItem.setOrder(existingOrder);

            return orderItem;
        }).toList();

        existingOrder.setItems(new ArrayList<>(updatedItems));

        orderMapper.updateFromDto(dto, existingOrder);

        return orderMapper.toDto(orderRepository.save(existingOrder));
    }


    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
