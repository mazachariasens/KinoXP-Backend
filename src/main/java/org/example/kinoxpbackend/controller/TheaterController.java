package org.example.kinoxpbackend.controller;


import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.service.TheaterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This class is a RestController, which means it will handle HTTP requests and return JSON responses.
@RequestMapping("/api/theaters") // Base URL for all endpoints in this controller.
public class TheaterController {

    private final TheaterService theaterService; // Dependency injection of the TheaterService.

    public TheaterController(TheaterService theaterService) { // Constructor injection of the TheaterService dependency.
        this.theaterService = theaterService;
    }

    @GetMapping // Endpoint to retrieve all theaters.
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters(); // Call the service method to get all theaters and return the result.
    }

}
