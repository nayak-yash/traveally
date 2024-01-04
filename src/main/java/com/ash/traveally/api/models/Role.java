package com.ash.traveally.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
