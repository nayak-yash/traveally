package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class PlaceDto {
    private Long id;
    private String name;
    private String city;
    private String country;
    private String description;
    private Integer price;
    private Float rating;
    private Boolean isFavourite;
    private String placePhoto;
    private String hotelPhoto;
    private Boolean hasWifi;
    private Boolean hasFood;
    private Boolean hasTV;
    private Boolean hasPool;
    private Boolean hasSpa;
    private Boolean hasLaundry;
    private Long hostId;
}