package org.example.kinoxpbackend.service;


import org.example.kinoxpbackend.dto.response.TheaterResponse;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // This annotation indicates that this class is a service component in the Spring context.
public class TheaterService {

    private final TheaterRepository theaterRepository; // Dependency injection of the TheaterRepository.

    public TheaterService(TheaterRepository theaterRepository) { // Constructor injection of the TheaterRepository dependency.
        this.theaterRepository = theaterRepository; // Assign the injected TheaterRepository to the class field.
    }
    //Nedestående blev tilføjet på grund af DTO response, så vi kan returnere et objekt med capacity i stedet for kun Theater entity.
    public List<TheaterResponse> getAllTheaters() { // Method to retrieve all theaters and map them to TheaterResponse DTOs.
        return theaterRepository.findAll() // Fetch all theaters from the repository.
                .stream() // Convert the list of theaters to a stream for processing.
                .map(theater -> new TheaterResponse(
                        theater.getTheaterId(),
                        theater.getName(),
                        theater.getRowCount(),
                        theater.getSeatsPerRow(),
                        calculateCapacity(theater)
                ))
                .toList(); // Collect the mapped TheaterResponse objects into a list and return it.

    }

    public int calculateCapacity(Theater theater) { // Method to calculate the total seating capacity of a theater.
        return theater.getRowCount() * theater.getSeatsPerRow();
    }
}
