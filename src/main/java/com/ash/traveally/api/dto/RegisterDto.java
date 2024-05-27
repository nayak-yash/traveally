package com.ash.traveally.api.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private Long phoneNumber;
    private String city;
    private String country;
    private String bio;
    private String password;
    private String photoUrl;
}
