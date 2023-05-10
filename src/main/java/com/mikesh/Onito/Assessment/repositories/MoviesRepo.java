package com.mikesh.Onito.Assessment.repositories;

import com.mikesh.Onito.Assessment.Entities.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MoviesRepo extends JpaRepository<Movies, String> {

    @Query(value = "SELECT m.*, SUM(r.num_votes) AS total_votes " +
            "FROM movies m " +
            "INNER JOIN ratings r ON m.tconst = r.tconst " +
            "GROUP BY m.genres, m.tconst " +
            "ORDER BY m.genres, total_votes DESC ", nativeQuery = true)
    List<Movies> findAllMoviesWithSubtotals();

    @Modifying
    @Transactional
    @Query("UPDATE Movies m SET m.runtimeMinutes = "
            + "CASE "
            + "WHEN m.genres = 'Documentary' THEN m.runtimeMinutes + 15 "
            + "WHEN m.genres = 'Animation' THEN m.runtimeMinutes + 30 "
            + "ELSE m.runtimeMinutes + 45 "
            + "END")
    void updateRuntimeMinutesByGenre();
}
