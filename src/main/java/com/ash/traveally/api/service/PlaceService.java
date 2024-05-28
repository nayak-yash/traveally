package com.ash.traveally.api.service;

import com.ash.traveally.api.dto.PlaceDto;
import com.ash.traveally.api.dto.PlaceResponse;

import java.util.List;


public interface PlaceService {

    PlaceDto createPlace(PlaceDto placeDto);

    List<PlaceDto> getAllPlace();

    PlaceResponse getAllPlace(int pageNo, int pageSize);

    PlaceDto getPlace(Long id);

    PlaceDto updatePlace(PlaceDto placeDto);

    void deletePlace(Long id);
}
