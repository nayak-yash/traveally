package com.ash.traveally.api.repository;

import com.ash.traveally.api.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}