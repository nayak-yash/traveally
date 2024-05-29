package com.ash.traveally.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String country;
    private String description;
    private Integer price;
    private Float rating;
    private String placePhoto;
    private String hotelPhoto;
    private Boolean hasWifi;
    private Boolean hasFood;
    private Boolean hasTV;
    private Boolean hasPool;
    private Boolean hasSpa;
    private Boolean hasLaundry;

    @ElementCollection
    private Set<Long> likedIDs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "host_id")
    @JsonBackReference
    private User host;
}