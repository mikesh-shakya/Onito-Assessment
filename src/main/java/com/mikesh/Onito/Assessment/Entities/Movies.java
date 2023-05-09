package com.mikesh.Onito.Assessment.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String titleType;
    private String primaryTitle;
    private int runtimeMinutes;
    private String genres;

}
