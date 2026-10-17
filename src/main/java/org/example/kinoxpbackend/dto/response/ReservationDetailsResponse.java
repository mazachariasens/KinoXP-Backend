package org.example.kinoxpbackend.dto.response;

import org.example.kinoxpbackend.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationDetailsResponse(
        Long reservationId,
        String customerName,
        String customerPhone,
        LocalDateTime createdAt,
        ReservationStatus status,
        List<String> seats

) {
}
