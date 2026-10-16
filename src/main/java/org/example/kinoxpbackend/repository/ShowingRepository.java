package org.example.kinoxpbackend.repository;

import jakarta.persistence.LockModeType;
import org.example.kinoxpbackend.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowingRepository extends JpaRepository<Showing, Long> {

    List<Showing> findAllByOrderByStartsAtAsc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT s
        FROM Showing s
        WHERE s.showingId = :showingId
        """)
    Optional<Showing> findByIdForReservation(
            @Param("showingId") Long showingId
    );
}