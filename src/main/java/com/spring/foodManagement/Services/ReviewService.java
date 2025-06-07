package com.spring.foodManagement.Services;

import com.spring.foodManagement.Dtos.ReviewCreateDto;
import com.spring.foodManagement.Dtos.ReviewResponseDto;
import com.spring.foodManagement.Exceptions.NotFoundException;
import com.spring.foodManagement.Mapper.ReviewMapper;
import com.spring.foodManagement.Models.Dish;
import com.spring.foodManagement.Models.Review;
import com.spring.foodManagement.Models.User;
import com.spring.foodManagement.Repositories.DishRepository;
import com.spring.foodManagement.Repositories.ReviewRepository;
import com.spring.foodManagement.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, DishRepository dishRepository,
                         UserRepository userRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with ID " + id + " not found"));
        return reviewMapper.toDto(review);
    }

    public ReviewResponseDto addReview(ReviewCreateDto dto) {
        Dish dish = dishRepository.findById(dto.getDishId())
                .orElseThrow(() -> new NotFoundException("Dish not found with ID: " + dto.getDishId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getUserId()));

        Review review = reviewMapper.toModel(dto);
        review.setDish(dish);
        review.setUser(user);

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewResponseDto updateReviewById(Long id, ReviewCreateDto dto) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with ID " + id + " not found"));

        Dish dish = dishRepository.findById(dto.getDishId())
                .orElseThrow(() -> new NotFoundException("Dish not found with ID: " + dto.getDishId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getUserId()));

        reviewMapper.update(dto, existing);
        existing.setDish(dish);
        existing.setUser(user);

        return reviewMapper.toDto(reviewRepository.save(existing));
    }

    public void deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with ID " + id + " not found"));
        reviewRepository.delete(review);
    }
}
