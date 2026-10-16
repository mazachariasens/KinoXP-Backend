package org.example.kinoxpbackend.dto.response;

import org.example.kinoxpbackend.entity.Reservation;
import org.example.kinoxpbackend.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
        Long reservationId,
        String customerName,
        String customerPhone,
        LocalDateTime createdAt,
        ReservationStatus status,
        Long showingId,
        List<Long> seatIds
) {
}
