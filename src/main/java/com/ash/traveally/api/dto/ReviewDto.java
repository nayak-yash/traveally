package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Integer id;
    private String content;
    private Integer likes;
}
