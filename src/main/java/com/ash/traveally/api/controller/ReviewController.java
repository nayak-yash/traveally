package com.ash.traveally.api.controller;


import com.ash.traveally.api.dto.ReviewDto;
import com.ash.traveally.api.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("place/{placeId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable int placeId, @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(placeId, reviewDto));
    }

    @GetMapping("place/{placeId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByPlaceId(@PathVariable int placeId) {
        return ResponseEntity.ok(reviewService.getReviewsByPlaceId(placeId));
    }

    @GetMapping("place/{placeId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int placeId, @PathVariable int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(placeId, reviewId);
        return ResponseEntity.ok(reviewDto);
    }

    @PutMapping("place/{placeId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable int placeId, @PathVariable int reviewId, @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(placeId, reviewId, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("place/{placeId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable int placeId, @PathVariable int reviewId) {
        reviewService.deleteReview(placeId, reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}