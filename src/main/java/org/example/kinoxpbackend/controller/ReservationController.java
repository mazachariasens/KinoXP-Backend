package org.example.kinoxpbackend.controller;


import org.example.kinoxpbackend.dto.request.CreateReservationRequest;
import org.example.kinoxpbackend.dto.response.ReservationResponse;
import org.example.kinoxpbackend.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<?> getActiveReservations(
            @RequestParam("showingId") Long showingId) {

        try {
            return ResponseEntity.ok(
                    reservationService.getActiveReservations(showingId)
            );
        }catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping //Post forespørgsel til /api/reservations for at oprette en ny reservation
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(
            @RequestBody CreateReservationRequest request) {

        return reservationService.createReservation(request);
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity<?> cancelReservation(
            @PathVariable Long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
