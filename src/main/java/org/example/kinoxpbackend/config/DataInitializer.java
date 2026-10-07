package org.example.kinoxpbackend.config;

import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.MovieRepository;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public DataInitializer(
            MovieRepository movieRepository,
            TheaterRepository theaterRepository) {

        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }

    @Override
    public void run(String... args) {


        if (movieRepository.count() == 0) {

            Movie movie1 = new Movie(
                    "Interstellar",
                    "Sci-Fi",
                    11,
                    169
            );

            Movie movie2 = new Movie(
                    "Titanic",
                    "Romance",
                    11,
                    194
            );

            Movie movie3 = new Movie(
                    "The Conjuring",
                    "Horror",
                    15,
                    112
            );

            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
        }


        if (theaterRepository.count() == 0) {

            theaterRepository.save(
                    new Theater("Lille sal", 20, 12)
            );

            theaterRepository.save(
                    new Theater("Stor sal", 25, 16)
            );
        }
    }
}
