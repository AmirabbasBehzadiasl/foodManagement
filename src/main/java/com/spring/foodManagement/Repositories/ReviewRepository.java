package com.spring.foodManagement.Repositories;

import com.spring.foodManagement.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
