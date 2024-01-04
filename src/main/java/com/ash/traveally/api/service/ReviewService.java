package com.ash.traveally.api.service;

import com.ash.traveally.api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int placeId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByPlaceId(int placeId);

    ReviewDto getReviewById(int placeId, int reviewId);

    ReviewDto updateReview(int placeId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int placeId, int reviewId);
}
