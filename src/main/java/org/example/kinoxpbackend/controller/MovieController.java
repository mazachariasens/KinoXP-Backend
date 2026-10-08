package org.example.kinoxpbackend.controller;

import org.example.kinoxpbackend.dto.CreateMovieRequest;
import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.service.MovieService;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
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
    @PostMapping
    public ResponseEntity<Movie> createMovie(
            @RequestBody CreateMovieRequest request) {
        Movie savedMovie = movieService.createMovie(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidMovie(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
    }


}


