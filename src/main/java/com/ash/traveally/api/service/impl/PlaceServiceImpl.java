package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.dto.PlaceDto;
import com.ash.traveally.api.dto.PlaceResponse;
import com.ash.traveally.api.exceptions.PlaceNotFoundException;
import com.ash.traveally.api.models.Place;
import com.ash.traveally.api.repository.PlaceRepository;
import com.ash.traveally.api.service.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public PlaceDto createPlace(PlaceDto placeDto) {
        Place Place = mapToEntity(placeDto);
        Place newPlace = placeRepository.save(Place);
        return mapToDto(newPlace);
    }

    @Override
    public PlaceResponse getAllPlace(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Place> page = placeRepository.findAll(pageable);
        List<Place> places = page.getContent();
        List<PlaceDto> content = places.stream().map(this::mapToDto).collect(Collectors.toList());
        PlaceResponse placeResponse = new PlaceResponse();
        placeResponse.setContent(content);
        placeResponse.setPageNo(page.getNumber());
        placeResponse.setPageSize(page.getSize());
        placeResponse.setTotalPages(page.getTotalPages());
        placeResponse.setTotalElements(page.getTotalElements());
        placeResponse.setLast(page.isLast());
        return placeResponse;
    }

    @Override
    public PlaceDto getPlace(int id) {
        Place place = placeRepository.findById(id).orElseThrow(() ->
                new PlaceNotFoundException("Place cannot be found")
        );
        return mapToDto(place);
    }

    @Override
    public PlaceDto updatePlace(PlaceDto placeDto, int id) {
        if (!placeRepository.existsById(id)) {
            throw new PlaceNotFoundException("Place could not be updated");
        }
        Place updatedPlace = mapToEntity(placeDto);
        Place newPlace = placeRepository.save(updatedPlace);
        return mapToDto(newPlace);
    }

    @Override
    public void deletePlace(int id) {
        if (!placeRepository.existsById(id)) {
            throw new PlaceNotFoundException("Place could not be deleted");
        }
        placeRepository.deleteById(id);
    }

    private PlaceDto mapToDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setTitle(place.getTitle());
        placeDto.setAddress(place.getAddress());
        placeDto.setDescription(place.getDescription());
        placeDto.setDays(place.getDays());
        placeDto.setNights(place.getNights());
        placeDto.setMaxGuest(place.getMaxGuest());
        placeDto.setPrice(place.getPrice());
        placeDto.setPerks(place.getPerks());
        placeDto.setUrls(place.getUrls());
        return placeDto;
    }

    private Place mapToEntity(PlaceDto placeDto)  {
        Place place = new Place();
        place.setId(placeDto.getId());
        place.setTitle(placeDto.getTitle());
        place.setAddress(placeDto.getAddress());
        place.setDescription(placeDto.getDescription());
        place.setDays(placeDto.getDays());
        place.setNights(placeDto.getNights());
        place.setMaxGuest(placeDto.getMaxGuest());
        place.setPrice(placeDto.getPrice());
        place.setPerks(placeDto.getPerks());
        place.setUrls(placeDto.getUrls());
        return place;
    }
}
