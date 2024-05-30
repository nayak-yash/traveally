package com.ash.traveally.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private Integer pageNo;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean last;
}
