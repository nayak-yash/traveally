package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private Integer userId;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken, Integer userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
