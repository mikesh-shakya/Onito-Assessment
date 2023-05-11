package com.mikesh.Onito.Assessment.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ratings {

    @Id
    private String tconst;
    private double averageRating;
    private int numVotes;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "tconst", referencedColumnName = "tconst")
    private Movies movies;
}
