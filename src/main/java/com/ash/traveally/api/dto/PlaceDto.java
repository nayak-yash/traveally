package com.ash.traveally.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaceDto {
    private Integer id;
    private String title;
    private String description;
    private String address;
    private Integer price;
    private Integer days;
    private Integer nights;
    private Integer maxGuest;
    private List<String> perks = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
}
