package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private String profileUrl;
    private String phoneNumber;
}
