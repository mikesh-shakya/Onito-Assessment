package com.mikesh.Onito.Assessment.Controllers;

import com.mikesh.Onito.Assessment.Entities.Movies;
import com.mikesh.Onito.Assessment.Helper.ExcelHelper;
import com.mikesh.Onito.Assessment.Services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
//        this.moviesService.saveListToDatabase(file);
//        return ResponseEntity.ok(Map.of("message", "File has been uploaded successfully."));
        if (ExcelHelper.checkExcelFormat(file)) {
            this.moviesService.saveListToDatabase(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

    @GetMapping("/allmovies")
    public List<Movies> getAllMovies(){
        return moviesService.getAllMovies();
    }

    @GetMapping("/api/v1/longest-duration-movies")
    public List<Movies> longestDurationMovies(){
        return moviesService.longestDurationMovies();
    }

    @PostMapping("/api/v1/new-movie")
    public Movies addMovie(@RequestBody Movies movie){
        return moviesService.addNewMovie(movie);
    }

    @GetMapping("/api/v1/top-rated-movies")
    public List<Movies> topRatedMovies(){
        return moviesService.topRatedMovies();
    }

    @GetMapping("/api/v1/genre-movies-with-subtotals")
    public List<Movies> genreMovieswithSubs(){
//        TODO: Write a SQL Query..
        return null;
    }

    @PostMapping("/api/v1/update-runtime-minutes")
    public List<Movies> updateRuntime(){
//        TODO:
        return null;
    }
}
