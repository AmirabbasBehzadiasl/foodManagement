package com.spring.foodManagement.Repositories;

import com.spring.foodManagement.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
