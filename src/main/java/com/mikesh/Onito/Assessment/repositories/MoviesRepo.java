package com.mikesh.Onito.Assessment.repositories;

import com.mikesh.Onito.Assessment.Entities.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepo extends JpaRepository<Movies, String> {
}
