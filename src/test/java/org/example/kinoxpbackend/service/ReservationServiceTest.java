package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.request.CreateReservationRequest;
import org.example.kinoxpbackend.dto.response.ReservationResponse;
import org.example.kinoxpbackend.entity.Reservation;
import org.example.kinoxpbackend.entity.Seat;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.ReservationRepository;
import org.example.kinoxpbackend.repository.ReservedSeatRepository;
import org.example.kinoxpbackend.repository.SeatRepository;
import org.example.kinoxpbackend.repository.ShowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//Unit-tests, repositories og services erstattes med mocs. Tester forretningslogik.
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservedSeatRepository reservedSeatRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ShowingRepository showingRepository;

    @Mock
    private ShowingService showingService;

    @Mock
    private Showing showing;

    @Mock
    private Theater theater;

    @Mock
    private Seat seat;

    @InjectMocks
    private ReservationService reservationService;

    private CreateReservationRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new CreateReservationRequest(
                10L,
                List.of(101L),
                "Testperson",
                "12345678"
        );
    }

    private void arrangeValidReservation() {
        when(showingRepository.findByIdForReservation(10L))
                .thenReturn(Optional.of(showing));

        when(showing.getShowingId()).thenReturn(10L);

        when(showingService.getReservableShowing(10L))
                .thenReturn(showing);

        when(showing.getTheater()).thenReturn(theater);
        when(theater.getTheaterId()).thenReturn(2L);

        when(seatRepository.findAllById(List.of(101L)))
                .thenReturn(List.of(seat));

        when(seat.getSeatId()).thenReturn(101L);
        when(seat.getTheater()).thenReturn(theater);

        when(reservedSeatRepository
                .findActiveReservedSeatIdsByShowingId(10L))
                .thenReturn(List.of());

        when(reservationRepository.saveAndFlush(
                any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void createReservationSucceedsWhenSeatIsAvailable() {
        // Arrange
        arrangeValidReservation();

        // Act
        ReservationResponse response =
                reservationService.createReservation(validRequest);

        // Assert
        assertEquals(10L, response.showingId());
        assertEquals(List.of(101L), response.seatIds());
        assertEquals("Testperson", response.customerName());
        assertEquals("12345678", response.customerPhone());

        ArgumentCaptor<Reservation> captor =
                ArgumentCaptor.forClass(Reservation.class);

        verify(reservationRepository)
                .saveAndFlush(captor.capture());

        Reservation saved = captor.getValue();

        assertEquals("Testperson", saved.getCustomerName());
        assertEquals("12345678", saved.getCustomerPhone());
        assertEquals(10L, saved.getShowing().getShowingId());

        verify(reservedSeatRepository).saveAll(anyList());
    }

    @Test
    void createReservationRejectsMissingShowingId() {
        // Arrange
        CreateReservationRequest request =
                new CreateReservationRequest(
                        null,
                        List.of(101L),
                        "Testperson",
                        "12345678"
                );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void createReservationRejectsEmptySeatList() {
        // Arrange
        CreateReservationRequest request =
                new CreateReservationRequest(
                        10L,
                        List.of(),
                        "Testperson",
                        "12345678"
                );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void createReservationRejectsDuplicateSeatIds() {
        // Arrange
        CreateReservationRequest request =
                new CreateReservationRequest(
                        10L,
                        List.of(101L, 101L),
                        "Testperson",
                        "12345678"
                );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void createReservationRejectsBlankCustomerName() {
        // Arrange
        CreateReservationRequest request =
                new CreateReservationRequest(
                        10L,
                        List.of(101L),
                        "   ",
                        "12345678"
                );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(request)
        );

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void createReservationRejectsUnknownShowing() {
        // Arrange
        when(showingRepository.findByIdForReservation(10L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(validRequest)
        );

        verify(reservationRepository, never())
                .saveAndFlush(any(Reservation.class));
    }

    @Test
    void createReservationRejectsSeatFromAnotherTheater() {
        // Arrange
        arrangeValidReservation();

        Theater otherTheater = mock(Theater.class);

        when(seat.getTheater()).thenReturn(otherTheater);
        when(otherTheater.getTheaterId()).thenReturn(99L);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(validRequest)
        );

        verify(reservationRepository, never())
                .saveAndFlush(any(Reservation.class));
    }

    @Test
    void createReservationRejectsAlreadyReservedSeat() {
        // Arrange
        arrangeValidReservation();

        when(reservedSeatRepository
                .findActiveReservedSeatIdsByShowingId(10L))
                .thenReturn(List.of(101L));

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.createReservation(validRequest)
        );

        verify(reservationRepository, never())
                .saveAndFlush(any(Reservation.class));
    }

    @Test
    void createReservationRejectsCancelledShowing() {
        // Arrange
        when(showingRepository.findByIdForReservation(10L))
                .thenReturn(Optional.of(showing));

        when(showing.getShowingId()).thenReturn(10L);

        when(showingService.getReservableShowing(10L))
                .thenThrow(new IllegalStateException(
                        "Cannot reserve a cancelled showing"));

        // Act + Assert
        assertThrows(
                IllegalStateException.class,
                () -> reservationService.createReservation(validRequest)
        );

        verify(reservationRepository, never())
                .saveAndFlush(any(Reservation.class));
    }
}