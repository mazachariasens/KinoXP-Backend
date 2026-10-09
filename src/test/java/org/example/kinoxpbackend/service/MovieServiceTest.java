package org.example.kinoxpbackend.service;
import org.example.kinoxpbackend.dto.CreateMovieRequest;
import org.example.kinoxpbackend.entity.Movie;
import org.example.kinoxpbackend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void createMovieWithValidData() {
        CreateMovieRequest request= new CreateMovieRequest(
                "Inception",
                "Science Fiction",
                15,
                148
        );
        when(movieRepository.save(any(Movie.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Movie result = movieService.createMovie(request);

        assertEquals("Inception", result.getTitle());
        assertEquals("Science Fiction", result.getCategory());
        assertEquals(15, result.getAgeLimit());
        assertEquals(148, result.getDuration());

        verify(movieRepository).save(result);
    }


    @Test
    void rejectBlankTitle(){
        CreateMovieRequest request = new CreateMovieRequest(
                "  ", "Science Fiction", 15, 148
        );

        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request)
        );
        verifyNoInteractions(movieRepository);
    }

    @Test
    void rejectEmptyCategory() {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception", "", 15, 148
        );
        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request));

        verifyNoInteractions(movieRepository);
    }

    @Test
    void rejectNegativeAgeLimit() {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception", "Science Fiction", -1, 148
        );
        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request));

        verifyNoInteractions(movieRepository);

    }


    @Test
    void rejectZeroDuration() {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception", "Science Fiction", 15, 0
        );

        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request));

    }

    @Test
    void rejectMissingAgeLimit() {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception", "Science Fiction", null, 148
        );
        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request));

        verifyNoInteractions(movieRepository);

    }

    @Test
    void rejectMissingDuration() {
        CreateMovieRequest request = new CreateMovieRequest(
                "Inception", "Science Fiction", 15, null
        );

        assertThrows(IllegalArgumentException.class,
                () -> movieService.createMovie(request));

    }










}
