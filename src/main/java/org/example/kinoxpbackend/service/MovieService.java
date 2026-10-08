package org.example.kinoxpbackend.service;
import org.example.kinoxpbackend.dto.CreateMovieRequest;
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

    public Movie createMovie(CreateMovieRequest request){

        if (request.title() == null || request.title().isBlank()){
            throw new IllegalArgumentException("Titel skal udfyldes.");
        }

        if (request.category() == null || request.category().isBlank()){
            throw new IllegalArgumentException("Kategori skal udfyldes.");
        }

        if (request.ageLimit() == null || request.ageLimit() < 0){
            throw new IllegalArgumentException("Aldersgrænse skal være 0 eller større.");
        }

        if (request.duration() == null || request.duration() <= 0){
            throw new IllegalArgumentException("Varighed skal være længere end 0 minutter.");
        }

        Movie movie = new Movie(
                request.title(),
                request.category(),
                request.ageLimit(),
                request.duration()
        );
        return movieRepository.save(movie);

    }



}
