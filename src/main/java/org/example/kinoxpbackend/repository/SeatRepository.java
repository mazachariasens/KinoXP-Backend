package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    long countByTheater_TheaterId(Long theaterId);

    List<Seat> findAllByTheater_TheaterIdOrderByRowNumberAscSeatNumberAsc(
            Long theaterId
    );
}

//findAllByTheater_TheaterIdOrderByRowNumberAscSeatNumberAsc(...) henter alle sæder i en sal i den rigtige rækkefølge.
//countByTheater_TheaterId(...) fortæller, hvor mange sæder der allerede er oprettet i en sal.