package com.ash.traveally.api.service.impl;

import com.ash.traveally.api.dto.PlaceDto;
import com.ash.traveally.api.dto.PlaceResponse;
import com.ash.traveally.api.exceptions.PlaceNotFoundException;
import com.ash.traveally.api.models.Place;
import com.ash.traveally.api.repository.PlaceRepository;
import com.ash.traveally.api.repository.UserRepository;
import com.ash.traveally.api.service.PlaceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PlaceDto createPlace(PlaceDto placeDto) {
        Place place = mapToEntity(placeDto);
        Place newPlace = placeRepository.save(place);
        return mapToDto(newPlace);
    }

    @Override
    public List<PlaceDto> getAllPlace() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(this::mapToDto).collect(Collectors.toList());
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
    public PlaceDto getPlace(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(() ->
                new PlaceNotFoundException("Place cannot be found")
        );
        return mapToDto(place);
    }

    @Override
    public PlaceDto updatePlace(PlaceDto placeDto) {
        if (!placeRepository.existsById(placeDto.getId())) {
            throw new PlaceNotFoundException("Place cannot be updated");
        }
        Place updatedPlace = mapToEntity(placeDto);
        Place newPlace = placeRepository.save(updatedPlace);
        return mapToDto(newPlace);
    }

    @Override
    public void deletePlace(Long id) {
        if (!placeRepository.existsById(id)) {
            throw new PlaceNotFoundException("Place cannot be deleted");
        }
        placeRepository.deleteById(id);
    }

    private String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

    public Long getUserId() {
        return userRepository.findIdFromEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }

    private PlaceDto mapToDto(Place place) {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(place.getId());
        placeDto.setName(place.getName());
        placeDto.setCity(place.getCity());
        placeDto.setCountry(place.getCountry());
        placeDto.setDescription(place.getDescription());
        placeDto.setPrice(place.getPrice());
        placeDto.setRating(place.getRating());
        placeDto.setPlacePhoto(place.getPlacePhoto());
        placeDto.setHotelPhoto(place.getHotelPhoto());
        placeDto.setHasWifi(place.getHasWifi());
        placeDto.setHasFood(place.getHasFood());
        placeDto.setHasTV(place.getHasTV());
        placeDto.setHasPool(place.getHasPool());
        placeDto.setHasSpa(place.getHasSpa());
        placeDto.setHasLaundry(place.getHasLaundry());
        placeDto.setHostId(place.getHost().getId());
        placeDto.setIsFavourite(place.getLikedIDs().contains(getUserId()));
        return placeDto;
    }

    private Place mapToEntity(PlaceDto placeDto)  {
        Place place = new Place();
        if (placeDto.getId() != null) place.setId(placeDto.getId());
        place.setName(placeDto.getName());
        place.setCity(placeDto.getCity());
        place.setCountry(placeDto.getCountry());
        place.setDescription(placeDto.getDescription());
        place.setPrice(placeDto.getPrice());
        place.setRating(placeDto.getRating());
        place.setPlacePhoto(placeDto.getPlacePhoto());
        place.setHotelPhoto(placeDto.getHotelPhoto());
        place.setHasWifi(placeDto.getHasWifi());
        place.setHasFood(placeDto.getHasFood());
        place.setHasTV(placeDto.getHasTV());
        place.setHasPool(placeDto.getHasPool());
        place.setHasSpa(placeDto.getHasSpa());
        place.setHasLaundry(placeDto.getHasLaundry());
        place.setHost(userRepository.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("Host doesn't exist")));
        if (placeDto.getId() != null) {
            Place temp = placeRepository.findById(placeDto.getId()).get();
            Set<Long> likedIds = temp.getLikedIDs();
            if (placeDto.getIsFavourite()) likedIds.add(getUserId());
            else likedIds.remove(getUserId());
            place.setLikedIDs(likedIds);
        }
        return place;
    }
}
