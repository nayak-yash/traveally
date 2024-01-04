package com.ash.traveally.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceResponse {
    private List<PlaceDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean last;
}
