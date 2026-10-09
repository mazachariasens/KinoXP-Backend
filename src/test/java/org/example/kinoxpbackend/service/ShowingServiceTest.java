package org.example.kinoxpbackend.service;
package org.example.kinoxpbackend.dto.response;
import org.example.kinoxpbackend.enums.ShowingStatus;
import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.ShowingRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowingServiceTest {

    @Mock
    private ShowingRepository showingRepository;

    @InjectMocks
    private ShowingService showingService;

    @Test
    void getAllShowingsReturnsShowingsInSortedOrder() {
        Showing first = createShowing(
                1L, LocalDateTime.of(2026, 10, 9, 18, 0),
                ShowingStatus.PLANNED);

        Showing second = createShowing(
                2L, LocalDateTime.of(2026, 10, 9, 20, 0),
                ShowingStatus.CANCELLED);

        when(showingRepository.findAllByOrderByStartsAtAsc())
                .thenReturn(List.of(first, second));

        List<ShowingResponse> result =
                showingService.getAllShowings();

        assertEquals(2, result.size());
        assertTrue(result.get(0).startsAt()
                .isBefore(result.get(1).startsAt()));

        verify(showingRepository, times(1))
                .findAllByOrderByStartsAtAsc();
    }

    @Test
    void getAllShowingsReturnsMovieAndTheaterNames() {
        Showing showing = createShowing(
                1L, LocalDateTime.of(2026, 10, 9, 18, 0),
                ShowingStatus.PLANNED);

        when(showingRepository.findAllByOrderByStartsAtAsc())
                .thenReturn(List.of(showing));

        ShowingResponse result =
                showingService.getAllShowings().get(0);

        assertEquals("The Matrix", result.movieTitle());
        assertEquals("Sal 1", result.theaterName());
        assertEquals(ShowingStatus.PLANNED, result.status());
    }

    private Showing createShowing(
            Long id,
            LocalDateTime startsAt,
            ShowingStatus status) {

        Movie movie = new Movie();
        movie.setTitle("The Matrix");

        Theater theater = new Theater();
        theater.setName("Sal 1");

        Showing showing = new Showing();
        showing.setShowingId(id);
        showing.setStartsAt(startsAt);
        showing.setStatus(status);
        showing.setMovie(movie);
        showing.setTheater(theater);

        return showing;
    }
}
