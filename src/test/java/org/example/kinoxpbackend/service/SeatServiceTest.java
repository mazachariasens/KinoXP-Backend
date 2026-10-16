package org.example.kinoxpbackend.service;

//VI tester at backend returnerer alle sæder for den rigtige biografsal, og at sædernes status beregnes korrekt.

import org.example.kinoxpbackend.dto.response.SeatStatusResponse;
import org.example.kinoxpbackend.entity.Seat;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.ReservedSeatRepository;
import org.example.kinoxpbackend.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReservedSeatRepository reservedSeatRepository;

    @Mock
    private ShowingService showingService;

    @Mock
    private Showing showing;

    @Mock
    private Theater theater;

    @Mock
    private Seat seat1;

    @Mock
    private Seat seat2;

    @InjectMocks
    private SeatService seatService;

    @Test
    void getSeatsForShowingReturnsCorrectSeatStatus() {
        //Arrange
        Long showingId = 10L;
        Long theaterId = 2L;

        when(showingService.getReservableShowing(showingId))
                .thenReturn(showing);

        when(showing.getTheater()).thenReturn(theater);
        when(theater.getTheaterId()).thenReturn(theaterId);

        when(seatRepository.findAllByTheater_TheaterIdOrderByRowNumberAscSeatNumberAsc(theaterId)).thenReturn(List.of(seat1, seat2));

        when(seat1.getSeatId()).thenReturn(101L);
        when(seat1.getRowNumber()).thenReturn(1);
        when(seat1.getSeatNumber()).thenReturn(1);

        when(seat2.getSeatId()).thenReturn(102L);
        when(seat2.getRowNumber()).thenReturn(1);
        when(seat2.getSeatNumber()).thenReturn(2);

        when(reservedSeatRepository
                .findActiveReservedSeatIdsByShowingId(showingId))
                .thenReturn(List.of(102L));

        //Act
        List<SeatStatusResponse> result =
                seatService.getSeatsForShowing(showingId);

        //Assert
        assertEquals(2, result.size());

        assertEquals(101L, result.get(0).seatId());
        assertEquals(1, result.get(0).rowNumber());
        assertEquals(1, result.get(0).seatNumber());
        assertFalse(result.get(0).reserved());

        assertEquals(102L, result.get(1).seatId());
        assertTrue(result.get(1).reserved());

        verify(seatRepository)
                .findAllByTheater_TheaterIdOrderByRowNumberAscSeatNumberAsc(theaterId);
    }

    @Test
    void getSeatsForShowingRejectsCancelledShowing() {
        //Arrange
        Long showingId = 10L;

        when(showingService.getReservableShowing(showingId))
                .thenThrow(new IllegalStateException("Du kan ikke reservere en Cancelled showing"));

        //Act + Assert
        assertThrows(IllegalStateException.class, () -> seatService.getSeatsForShowing(showingId));

        verifyNoInteractions(seatRepository);
        verifyNoInteractions(reservedSeatRepository);

    }
}
