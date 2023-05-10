package com.mikesh.Onito.Assessment.repositories;

import com.mikesh.Onito.Assessment.Entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Ratings, String> {
}
