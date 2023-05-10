package com.mikesh.Onito.Assessment.Controllers;

import com.mikesh.Onito.Assessment.Entities.Movies;
import com.mikesh.Onito.Assessment.Entities.Ratings;
import com.mikesh.Onito.Assessment.Services.MoviesService;
import com.mikesh.Onito.Assessment.repositories.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class DataMergeController {

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private RatingRepo ratingRepo;

    @PostMapping("/movie/upload")
    public ResponseEntity<?> movieupload(@RequestParam("file") MultipartFile file){
        this.moviesService.saveMovieListToDatabase(file);
        return ResponseEntity.ok(Map.of("message", "File has been uploaded successfully."));
    }
    @PostMapping("/rating/upload")
    public ResponseEntity<?> ratingupload(@RequestParam("file") MultipartFile file){
        this.moviesService.saveRatingListToDatabase(file);
        return ResponseEntity.ok(Map.of("message", "File has been uploaded successfully."));
    }
    @GetMapping("/allmovies")
    public List<Movies> getAllMovies(){
        return moviesService.getAllMovies();
    }

    @GetMapping("/allratings")
    public List<Ratings> getAllRating(){
        return ratingRepo.findAll();
    }

    @GetMapping("/api/v1/longest-duration-movies")
    public List<Movies> longestDurationMovies(){
        return moviesService.longestDurationMovies();
    }

    @PostMapping("/api/v1/new-movie")
    public ResponseEntity<?> addMovie(@RequestBody Movies movie){
        moviesService.addNewMovie(movie);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/api/v1/top-rated-movies")
    public  List<Map<String, Object>> topRatedMovies(){
        return moviesService.topRatedMovies();
    }

    @GetMapping("/api/v1/genre-movies-with-subtotals")
    public List<Movies> genreMovieswithSubs(){
        return moviesService.genreMovieWithSubtotals();
    }

    @PostMapping("/api/v1/update-runtime-minutes")
    public ResponseEntity<?> updateRuntime(){
         moviesService.updateRunTime();
         return ResponseEntity.ok("Updated");
    }
}
