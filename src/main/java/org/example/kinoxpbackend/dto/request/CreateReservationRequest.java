package org.example.kinoxpbackend.dto.request;

import java.util.List;

public record CreateReservationRequest(
        Long showingId,
        List<Long> seatIds,
        String customerName,
        String customerPhone
) {
}
