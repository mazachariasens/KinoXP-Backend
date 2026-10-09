
package org.example.kinoxpbackend.controller;

import org.example.kinoxpbackend.dto.response.SeatStatusResponse;
import org.example.kinoxpbackend.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{showingId}/seats")
    public List<SeatStatusResponse> getSeatsForShowing(
            @PathVariable Long showingId) {

        return seatService.getSeatsForShowing(showingId);
    }
}
