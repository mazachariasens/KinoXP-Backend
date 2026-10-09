package org.example.kinoxpbackend.config;

import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.entity.Seat;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.MovieRepository;
import org.example.kinoxpbackend.repository.SeatRepository;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;

    public DataInitializer(
            MovieRepository movieRepository,
            TheaterRepository theaterRepository,
            SeatRepository seatRepository) {

        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) {

// Opret film, hvis databasen ikke allerede indeholder film
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

            movieRepository.saveAll(
                    List.of(movie1, movie2, movie3)
            );
        }

// Opret biografsale, hvis databasen ikke allerede indeholder nogen
        if (theaterRepository.count() == 0) {

            theaterRepository.save(
                    new Theater("Lille sal", 20, 12)
            );

            theaterRepository.save(
                    new Theater("Stor sal", 25, 16)
            );
        }

        // Opret sæder for hver sal, hvis sæderne ikke allerede findes

        for (Theater theater : theaterRepository.findAll()) {

            if (seatRepository.countByTheater_TheaterId(
                    theater.getTheaterId()) == 0) {

                List<Seat> seats = new ArrayList<>();

                for (int row = 1; row <= theater.getRowCount(); row++) {

                    for (int seatNumber = 1;
                    seatNumber <= theater.getSeatsPerRow();
                    seatNumber++) {

                        Seat seat = new Seat(theater, row, seatNumber);
                        seats.add(seat);
                    }
                }
                seatRepository.saveAll(seats);
            }
        }
    }
}
