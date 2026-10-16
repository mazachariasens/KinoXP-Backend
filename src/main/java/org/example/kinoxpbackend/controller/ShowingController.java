package org.example.kinoxpbackend.controller;
import org.example.kinoxpbackend.dto.request.CreateShowingRequest;
import org.example.kinoxpbackend.dto.response.ShowingResponse;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.service.ShowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
    @PostMapping
    public ResponseEntity<?> createShowing(
            @RequestBody CreateShowingRequest request) {
        try {
            Showing showing = showingService.createShowing(
                    request.getMovieId(),
                    request.getTheaterId(),
                    request.getStartsAt()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(showing);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{showingId}/cancel")
    public ResponseEntity<?> cancelShowing(
            @PathVariable Long showingId) {
        try {
            return ResponseEntity.ok(
                    showingService.cancelShowing(showingId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    catch (
    NoSuchElementException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }
}
}

