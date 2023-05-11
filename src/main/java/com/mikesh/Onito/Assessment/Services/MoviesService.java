package com.mikesh.Onito.Assessment.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesh.Onito.Assessment.Entities.Movies;
import com.mikesh.Onito.Assessment.Entities.Ratings;
import com.mikesh.Onito.Assessment.Helper.MoviesExcelHelper;
import com.mikesh.Onito.Assessment.Helper.RatingExcelHelper;
import com.mikesh.Onito.Assessment.repositories.MoviesRepo;
import com.mikesh.Onito.Assessment.repositories.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public  List<Movies> topRatedMovies() {
        List<Movies> topratedmovies = this.moviesRepo.findAll().stream()
                                        .filter(movie -> movie.getRatings().getAverageRating() > 6.0)
                                        .sorted(Comparator.comparing(movies -> movies.getRatings().getAverageRating()))
                                        .toList();
        return topratedmovies;
    }

    public String genreMovieWithSubtotals() throws JsonProcessingException {

        List<Object[]> result = this.moviesRepo.findAllMoviesWithSubtotals();
        List<Object[]> genreTotals = this.moviesRepo.getGenreTotals();

        for(Object[] row : genreTotals){
            String genre = (String) row[0];
            List<Object[]> movi = new ArrayList<>();
            for(Object[] mov : result){
                if(genre.equals(mov[0])){
                    Object[] obj = new Object[2];
                    obj[0] = mov[1];
                    obj[1] = mov[3];
                    movi.add(obj);
                }
            }
            row[2] = movi;
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(genreTotals);
    }

    public void updateRunTime() {
         this.moviesRepo.updateRuntimeMinutesByGenre();
    }
}
