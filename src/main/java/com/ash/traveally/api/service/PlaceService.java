package com.ash.traveally.api.service;

import com.ash.traveally.api.dto.PlaceDto;
import com.ash.traveally.api.dto.PlaceResponse;


public interface PlaceService {

    PlaceDto createPlace(PlaceDto placeDto);

    PlaceResponse getAllPlace(int pageNo, int pageSize);

    PlaceDto getPlace(int id);

    PlaceDto updatePlace(PlaceDto placeDto, int id);

    void deletePlace(int id);
}
