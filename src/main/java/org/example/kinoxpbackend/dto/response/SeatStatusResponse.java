package org.example.kinoxpbackend.dto.response;

public record SeatStatusResponse(
        Long seatId,
        int rowNumber,
        int seatNumber,
        boolean reserved
) {
}