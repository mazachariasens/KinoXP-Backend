package org.example.kinoxpbackend.service;


import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository; // Dependency injection of the TheaterRepository.

    public TheaterService(TheaterRepository theaterRepository) { // Constructor injection of the TheaterRepository dependency.
        this.theaterRepository = theaterRepository; // Assign the injected TheaterRepository to the class field.
    }

    public List<Theater> getAllTheaters() { // Method to retrieve all theaters from the repository.
        return theaterRepository.findAll(); // Call the findAll() method of the TheaterRepository to get a list of all theaters.
    }

    public int calculateCapacity(Theater theater) { // Method to calculate the total seating capacity of a theater.
        return theater.getRowCount() * theater.getSeatsPerRow();
    }
}
