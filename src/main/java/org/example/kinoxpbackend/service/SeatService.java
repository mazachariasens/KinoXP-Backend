package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.response.SeatStatusResponse;
import org.example.kinoxpbackend.entity.Seat;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.repository.ReservedSeatRepository;
import org.example.kinoxpbackend.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final ReservedSeatRepository reservedSeatRepository;
    private final ShowingService showingService;

    public SeatService(
            SeatRepository seatRepository,
            ReservedSeatRepository reservedSeatRepository,
            ShowingService showingService) {

        this.seatRepository = seatRepository;
        this.reservedSeatRepository = reservedSeatRepository;
        this.showingService = showingService;
    }

    @Transactional(readOnly = true)
    public List<SeatStatusResponse> getSeatsForShowing(Long showingId) {

        Showing showing =
                showingService.getReservableShowing(showingId);

        Long theaterId = showing.getTheater().getTheaterId();

        List<Seat> seats =
                seatRepository
                        .findAllByTheater_TheaterIdOrderByRowNumberAscSeatNumberAsc(
                                theaterId
                        );

        Set<Long> reservedSeatIds =
                Set.copyOf(
                        reservedSeatRepository
                                .findReservedSeatIdsByShowingId(showingId)
                );

        return seats.stream()
                .map(seat -> new SeatStatusResponse(
                        seat.getSeatId(),
                        seat.getRowNumber(),
                        seat.getSeatNumber(),
                        reservedSeatIds.contains(seat.getSeatId())
                ))
                .toList();
    }
}