package com.ash.traveally.api.repository;

import com.ash.traveally.api.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByPlaceId(int placeId);
}
