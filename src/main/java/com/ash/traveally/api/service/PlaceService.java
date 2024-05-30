package com.ash.traveally.api.service;

import com.ash.traveally.api.dto.PageResponse;
import com.ash.traveally.api.dto.PlaceDto;

import java.util.List;


public interface PlaceService {

    PlaceDto createPlace(PlaceDto placeDto);

    List<PlaceDto> getAllPlaces();

    PageResponse<PlaceDto> getAllPlaces(int pageNo, int pageSize);

    PlaceDto getPlace(Long id);

    PlaceDto updatePlace(PlaceDto placeDto);

    void deletePlace(Long id);
}
