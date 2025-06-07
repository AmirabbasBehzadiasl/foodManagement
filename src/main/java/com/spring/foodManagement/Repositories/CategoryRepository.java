package com.spring.foodManagement.Repositories;

import com.spring.foodManagement.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
