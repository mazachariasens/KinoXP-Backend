package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.response.ShowingResponse;
import org.example.kinoxpbackend.entity.Showing;
import org.example.kinoxpbackend.enums.ShowingStatus;
import org.example.kinoxpbackend.repository.MovieRepository;
import org.example.kinoxpbackend.repository.ShowingRepository;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShowingService {
    private final ShowingRepository showingRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public ShowingService(ShowingRepository showingRepository, MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.showingRepository = showingRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }
    @Transactional(readOnly = true)
    public List<ShowingResponse> getAllShowings(){
        return showingRepository.findAllByOrderByStartsAtAsc().stream().map(this::toResponse).toList();

    }

    private ShowingResponse toResponse(Showing showing) {
        return new ShowingResponse(showing.getShowingId(),
                showing.getStartsAt(), showing.getStatus(), showing.getMovie().getTitle(), showing.getTheater().getName()
        );
    }
        @Transactional
        public Showing createShowing(
                Long movieId,
                Long theaterId,
                LocalDateTime startsAt) {

            var movie = movieRepository.findById(movieId)
                    .orElseThrow(() ->
                            new NoSuchElementException("Movie not found"));

            var theater = theaterRepository.findById(theaterId)
                    .orElseThrow(() ->
                            new NoSuchElementException("Theater not found"));

            Showing showing = new Showing();
            showing.setMovie(movie);
            showing.setTheater(theater);
            showing.setStartsAt(startsAt);
            showing.setStatus(ShowingStatus.PLANNED);

            return showingRepository.save(showing);
        }

        @Transactional
        public Showing cancelShowing(Long showingId) {
            Showing showing = showingRepository.findById(showingId)
                    .orElseThrow(() ->
                            new NoSuchElementException("Showing not found"));

            showing.setStatus(ShowingStatus.CANCELLED);

            return showingRepository.save(showing);
        }

        @Transactional(readOnly = true)
        public Showing getReservableShowing(Long showingId) {
            Showing showing = showingRepository.findById(showingId)
                    .orElseThrow(() ->
                            new NoSuchElementException("Showing not found"));

            if (showing.getStatus() == ShowingStatus.CANCELLED) {
                throw new IllegalStateException(
                        "Cannot reserve a cancelled showing");
            }

            return showing;
        }
    }
