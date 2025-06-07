package com.spring.foodManagement.Controllers;

import com.spring.foodManagement.Dtos.OrderCreateDto;
import com.spring.foodManagement.Dtos.OrderResponseDto;
import com.spring.foodManagement.Services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found");
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<?> getOrderById(@RequestParam Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@Valid @RequestBody OrderCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(dto));
    }

    @PutMapping("/updateOrderById")
    public ResponseEntity<?> updateOrderById(@RequestParam Long id, @Valid @RequestBody OrderCreateDto dto) {
        return ResponseEntity.ok(orderService.updateOrderById(id, dto));
    }

    @DeleteMapping("/deleteOrderById")
    public ResponseEntity<?> deleteOrderById(@RequestParam Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Order with id " + id + " deleted successfully");
    }
}
