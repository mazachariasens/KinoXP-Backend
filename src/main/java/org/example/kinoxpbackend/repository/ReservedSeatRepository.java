package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservedSeatRepository
        extends JpaRepository<ReservedSeat, Long> {

    @Query("""
        select rs.seat.seatId
        from ReservedSeat rs
        where rs.showing.showingId = :showingId
        """)
    List<Long> findReservedSeatIdsByShowingId(
            @Param("showingId") Long showingId
    );
}