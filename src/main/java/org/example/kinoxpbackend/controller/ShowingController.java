package org.example.kinoxpbackend.controller;
import com.example.kinoxpbackend.dto.ShowingResponse;

import org.example.kinoxpbackend.service.ShowingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class ShowingController {

    private final ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @GetMapping
    public List<ShowingResponse> getAllShowings() {
        return showingService.getAllShowings();
    }
}
}
