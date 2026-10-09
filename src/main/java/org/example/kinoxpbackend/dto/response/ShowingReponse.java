package org.example.kinoxpbackend.dto.response;

import org.example.kinoxpbackend.enums.ShowingStatus;

import java.time.LocalDateTime;

public record ShowingReponse(
        Long showingId,
        LocalDateTime startsAt,
        ShowingStatus status,
        String movieTitle,
        String theaterName
) {


}
