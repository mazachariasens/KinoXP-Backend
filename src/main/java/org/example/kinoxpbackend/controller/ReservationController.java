package org.example.kinoxpbackend.controller;


import org.example.kinoxpbackend.dto.request.CreateReservationRequest;
import org.example.kinoxpbackend.dto.response.ReservationResponse;
import org.example.kinoxpbackend.entity.Reservation;
import org.example.kinoxpbackend.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping //Post forespørgsel til /api/reservations for at oprette en ny reservation
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(
            @RequestBody CreateReservationRequest request) {

        return reservationService.createReservation(request);
    }
}
