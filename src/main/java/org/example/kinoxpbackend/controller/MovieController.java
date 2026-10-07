package org.example.kinoxpbackend.controller;

import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/movies")

public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public List <Movie> getAllMovies(){
        return movieService.getAllMovies();
    }
}

