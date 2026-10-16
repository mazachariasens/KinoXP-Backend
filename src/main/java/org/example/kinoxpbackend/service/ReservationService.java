package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.request.CreateReservationRequest;
import org.example.kinoxpbackend.dto.response.ReservationResponse;
import org.example.kinoxpbackend.entity.Reservation;
import org.example.kinoxpbackend.entity.ReservedSeat;
import org.example.kinoxpbackend.entity.Seat;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.enums.ReservationStatus;
import org.example.kinoxpbackend.repository.ReservationRepository;
import org.example.kinoxpbackend.repository.ReservedSeatRepository;
import org.example.kinoxpbackend.repository.SeatRepository;
import org.example.kinoxpbackend.repository.ShowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservedSeatRepository reservedSeatRepository;
    private final SeatRepository seatRepository;
    private final ShowingRepository showingRepository;
    private final ShowingService showingService;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservedSeatRepository reservedSeatRepository,
            SeatRepository seatRepository,
            ShowingRepository showingRepository,
            ShowingService showingService) {

        this.reservationRepository = reservationRepository;
        this.reservedSeatRepository = reservedSeatRepository;
        this.seatRepository = seatRepository;
        this.showingRepository = showingRepository;
        this.showingService = showingService;
    }

    @Transactional
    public ReservationResponse createReservation(
            CreateReservationRequest request) {

        //Kontroller input
        if (request == null || request.showingId() == null) {
            throw new IllegalArgumentException("Vælg en forestilling");
        }

        if (request.seatIds() == null || request.seatIds().isEmpty()) {
            throw new IllegalArgumentException("Vælg mindst ét sæde.");
        }

        if (request.seatIds().stream().anyMatch(id -> id == null)) {
            throw new IllegalArgumentException("Et sæde-ID må ikke være tomt.");
        }

        if (new HashSet<>(request.seatIds()).size() != request.seatIds().size()) {
            throw new IllegalArgumentException("Det samme sæde er valgt flere gange.");
        }

        if (request.customerName() == null
                || request.customerName().isBlank()
                || request.customerPhone() == null
                || request.customerPhone().isBlank()) {
            throw new IllegalArgumentException("Navn og telefonnummer skal udfyldes.");
        }

        //Lås showing og kontrollér at den kan reserveres
        Showing lockedShowing = showingRepository
                .findByIdForReservation(request.showingId())
                .orElseThrow(() -> new IllegalArgumentException("Forestillingen findes ikke."));

        Showing showing = showingService.getReservableShowing(lockedShowing.getShowingId());


        //Find sæderne
        List<Long> seatIds = request.seatIds();
        List<Seat> seats = seatRepository.findAllById(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Et eller flere sæder findes ikke.");
        }

        //Kontroller at alle sæder tilhører forestillingens sal.

        Long theaterId = showing.getTheater().getTheaterId();
        Boolean seatsBelongToTheater = seats.stream()
                .allMatch(seat -> seat.getTheater().getTheaterId().equals(theaterId));

        if (!seatsBelongToTheater) {
            throw new IllegalArgumentException("Et eller flere sæder tilhører en forkert sal.");
        }

        // Kontroller at sæderne er ledige

        Set<Long> reservedSeatIds = new HashSet<>(
                reservedSeatRepository
                        .findActiveReservedSeatIdsByShowingId(
                                showing.getShowingId()));
        boolean seatAlreadyReserved = seatIds.stream()
                .anyMatch(reservedSeatIds::contains);

        if (seatAlreadyReserved) {
            throw new IllegalArgumentException(
                    "Et eller flere sæder er allerede reserverede.");

        }

        //Opret reservation

        Reservation reservation = new Reservation();
        reservation.setCustomerName(request.customerName().trim());
        reservation.setCustomerPhone(request.customerPhone().trim());
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setShowing(showing);

        Reservation savedReservation = reservationRepository.saveAndFlush(reservation);


        //Knyt sæderne til reservationen

        List<ReservedSeat> reservedSeats = seats.stream()
                .map(seat -> new ReservedSeat(savedReservation, seat))
                .toList();

        reservedSeatRepository.saveAll(reservedSeats);


        //Returner svaret til frontend
        return new ReservationResponse(
                savedReservation.getReservationId(),
                savedReservation.getCustomerName(),
                savedReservation.getCustomerPhone(),
                savedReservation.getCreatedAt(),
                savedReservation.getStatus(),
                showing.getShowingId(),
                seats.stream()
                        .map(Seat::getSeatId)
                        .toList()
        );
    }
}