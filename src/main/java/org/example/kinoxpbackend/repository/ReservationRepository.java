package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Reservation;
import org.example.kinoxpbackend.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {
    List<Reservation> findByShowing_ShowingIdAndStatusOrderByCreatedAtDesc(
            Long showingId, ReservationStatus status
    );
}
