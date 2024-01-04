package com.ash.traveally.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String address;
    private Integer price;
    private Integer days;
    private Integer nights;
    private Integer maxGuest;

    @ElementCollection
    private List<String> perks = new ArrayList<>();

    @ElementCollection
    private List<String> urls = new ArrayList<>();

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}