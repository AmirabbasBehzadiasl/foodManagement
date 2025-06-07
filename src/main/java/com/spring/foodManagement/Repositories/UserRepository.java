package com.spring.foodManagement.Repositories;

import com.spring.foodManagement.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.role = 'CHEF'")
    Optional<User> findChefById(@Param("id") Long id);
}
