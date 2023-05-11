package com.mikesh.Onito.Assessment.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movies {
    @Id
    private String tconst;
    @JsonIgnore
    private String titleType;
    private String primaryTitle;
    private int runtimeMinutes;
    private String genres;

    @JsonIgnore
    @OneToOne(mappedBy = "movies")
    private Ratings ratings;
}
