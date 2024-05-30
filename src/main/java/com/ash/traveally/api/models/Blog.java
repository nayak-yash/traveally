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
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private String title;
    private String introduction;
    private String description;
    private String placePhoto;
    private Long time;

    @ElementCollection
    private Set<Long> likedIDs = new HashSet<>();

    @ElementCollection
    private Set<Long> savedIDs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private User author;
}