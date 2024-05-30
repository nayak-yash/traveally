package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class BlogDto {
    private Long id;
    private String city;
    private String country;
    private String title;
    private String introduction;
    private String description;
    private Boolean isFavourite;
    private String placePhoto;
    private Integer likes;
    private Long time;
    private Long authorId;
}
