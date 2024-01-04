package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.dto.ReviewDto;
import com.ash.traveally.api.exceptions.PlaceNotFoundException;
import com.ash.traveally.api.exceptions.ReviewNotFoundException;
import com.ash.traveally.api.models.Place;
import com.ash.traveally.api.models.Review;
import com.ash.traveally.api.repository.PlaceRepository;
import com.ash.traveally.api.repository.ReviewRepository;
import com.ash.traveally.api.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PlaceRepository placeRepository) {
        this.reviewRepository = reviewRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public ReviewDto createReview(int placeId, ReviewDto reviewDto) {
        Place place = placeRepository.findById(placeId).orElseThrow(() ->
                new PlaceNotFoundException("Place with associated review not found")
        );
        Review review = mapToEntity(reviewDto);
        review.setPlace(place);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPlaceId(int placeId) {
        List<Review> reviews = reviewRepository.findByPlaceId(placeId);
        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int placeId, int reviewId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException("Place with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate place not found"));
        if (review.getPlace().getId().intValue() == place.getId().intValue()) {
            return mapToDto(review);
        }
        throw new ReviewNotFoundException("This review does not belong to a place");
    }

    @Override
    public ReviewDto updateReview(int placeId, int reviewId, ReviewDto reviewDto) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException("Place with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate place not found"));
        if(review.getPlace().getId().intValue() != place.getId().intValue()) {
            throw new ReviewNotFoundException("This review does not belong to a place");
        }
        Review updatedReview = mapToEntity(reviewDto);
        updatedReview.setPlace(place);
        Review savedReview = reviewRepository.save(updatedReview);
        return mapToDto(savedReview);
    }

    @Override
    public void deleteReview(int placeId, int reviewId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException("Place with associated review not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate place not found"));
        if(review.getPlace().getId().intValue() != place.getId().intValue()) {
            throw new ReviewNotFoundException("This review does not belong to a place");
        }
        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setContent(review.getContent());
        reviewDto.setLikes(review.getLikes());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setContent(reviewDto.getContent());
        review.setLikes(reviewDto.getLikes());
        return review;
    }
}
