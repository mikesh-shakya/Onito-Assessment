package com.mikesh.Onito.Assessment.Services;

import com.mikesh.Onito.Assessment.Entities.Movies;
import com.mikesh.Onito.Assessment.Helper.ExcelHelper;
import com.mikesh.Onito.Assessment.repositories.MoviesRepo;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepo moviesRepo;
    public void saveListToDatabase(MultipartFile file){

        try {
            List<Movies> moviesList = ExcelHelper.convertExcelToList(file.getInputStream());
            this.moviesRepo.saveAll(moviesList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movies> getAllMovies(){
        return this.moviesRepo.findAll();
    }


}
