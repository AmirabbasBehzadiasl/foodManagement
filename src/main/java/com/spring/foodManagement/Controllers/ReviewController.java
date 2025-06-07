package com.spring.foodManagement.Controllers;

import com.spring.foodManagement.Dtos.ReviewCreateDto;
import com.spring.foodManagement.Dtos.ReviewResponseDto;
import com.spring.foodManagement.Services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getAllReviews")
    public ResponseEntity<?> getAllReviews() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found");
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/getReviewById")
    public ResponseEntity<?> getReviewById(@RequestParam Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(dto));
    }

    @PutMapping("/updateReviewById")
    public ResponseEntity<?> updateReviewById(@RequestParam Long id, @Valid @RequestBody ReviewCreateDto dto) {
        return ResponseEntity.ok(reviewService.updateReviewById(id, dto));
    }

    @DeleteMapping("/deleteReviewById")
    public ResponseEntity<?> deleteReviewById(@RequestParam Long id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Review deleted successfully");
    }
}
