package com.mikesh.Onito.Assessment.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movies {
    @Id
    private String tconst;
    private String titleType;

}
