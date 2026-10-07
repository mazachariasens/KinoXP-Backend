package org.example.kinoxpbackend.service;
import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService (MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List <Movie> getAllMovies(){
        return movieRepository.findAll();
    }




}
