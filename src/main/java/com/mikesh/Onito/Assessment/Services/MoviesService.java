package com.mikesh.Onito.Assessment.Services;

import com.mikesh.Onito.Assessment.Entities.Movies;
import com.mikesh.Onito.Assessment.Entities.Ratings;
import com.mikesh.Onito.Assessment.Helper.MoviesExcelHelper;
import com.mikesh.Onito.Assessment.Helper.RatingExcelHelper;
import com.mikesh.Onito.Assessment.repositories.MoviesRepo;
import com.mikesh.Onito.Assessment.repositories.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MoviesService {
    @Autowired
    private MoviesRepo moviesRepo;
    @Autowired
    private RatingRepo ratingRepo;

    public void saveMovieListToDatabase(MultipartFile file){
        try {
            List<Movies> moviesList = MoviesExcelHelper.convertExcelToList(file.getInputStream());
            this.moviesRepo.saveAll(moviesList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveRatingListToDatabase(MultipartFile file){
        try {
            List<Ratings> ratingsList = RatingExcelHelper.convertExcelToList(file.getInputStream());
            this.ratingRepo.saveAll(ratingsList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Movies> getAllMovies(){
        return this.moviesRepo.findAll();
    }

    public List<Movies> longestDurationMovies() {
        List<Movies> sorted = this.moviesRepo.findAll().stream()
                .sorted(Comparator.comparing(Movies::getRuntimeMinutes).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return sorted;
    }

    public void addNewMovie(Movies movie) {
        this.moviesRepo.save(movie);
    }

    public  List<Map<String, Object>> topRatedMovies() {
        List<Movies> topratedmovies = this.moviesRepo.findAll().stream()
                                        .filter(movie -> movie.getRatings().getAverageRating() > 6.0)
                                        .sorted(Comparator.comparing(movies -> movies.getRatings().getAverageRating()))
                                        .toList();

        List<Map<String, Object>> output = topratedmovies.stream()
                .map(movie -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("tconst", movie.getTconst());
                    map.put("primaryTitle", movie.getPrimaryTitle());
                    map.put("genre", movie.getGenres());
                    map.put("averageRating", movie.getRatings().getAverageRating());
                    return map;
                })
                .collect(Collectors.toList());

        return output;
    }

    public List<Movies> genreMovieWithSubtotals() {
        return this.moviesRepo.findAllMoviesWithSubtotals();
    }

    public void updateRunTime() {
         this.moviesRepo.updateRuntimeMinutesByGenre();
    }
}
