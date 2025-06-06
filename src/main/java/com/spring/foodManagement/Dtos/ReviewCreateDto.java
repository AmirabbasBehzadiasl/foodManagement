package com.spring.foodManagement.Dtos;

import jakarta.validation.constraints.*;

public class ReviewCreateDto {

    @NotBlank(message = "Comment is required")
    @Size(max = 500, message = "Comment must be less than 500 characters")
    private String comment;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @NotNull(message = "Dish ID is required")
    private Long dishId;

    @NotNull(message = "User ID is required")
    private Long userId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
