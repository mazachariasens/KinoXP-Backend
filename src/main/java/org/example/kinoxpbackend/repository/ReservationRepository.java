package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {
}
