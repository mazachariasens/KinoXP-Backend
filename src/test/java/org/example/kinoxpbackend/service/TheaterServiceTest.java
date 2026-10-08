package org.example.kinoxpbackend.service;

import org.example.kinoxpbackend.dto.response.TheaterResponse;
import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/*
 * Vi bruger Mockito til at teste TheaterService isoleret.
 *
 * TheaterService er afhængig af TheaterRepository.
 * I stedet for at bruge en rigtig database laver vi et "mock"
 * af repository'et, så vi selv bestemmer, hvilke data repository'et returnerer.
 */
@ExtendWith(MockitoExtension.class)
class TheaterServiceTest {

    // Mock betyder, at vi laver en falsk version af TheaterRepository.
    // Vi bruger ikke den rigtige database i disse tests.
    @Mock
    private TheaterRepository theaterRepository;

    // @InjectMocks opretter en TheaterService og indsætter vores mock
    // TheaterRepository i den.
    @InjectMocks
    private TheaterService theaterService;


    /*
     * TEST 1:
     *
     * Vi tester, at capacity bliver beregnet korrekt.
     *
     * Lille sal:
     * 20 rækker × 12 sæder = 240 pladser
     */
    @Test
    void calculateCapacityReturnsCorrectCapacity() {

        // Arrange:
        // Vi opretter en Theater, som vi vil teste beregningen på.
        Theater theater = new Theater("Lille sal", 20, 12);

        // Act:
        // Vi kalder den metode i TheaterService, som beregner kapaciteten.
        int capacity = theaterService.calculateCapacity(theater);

        // Assert:
        // Vi forventer, at resultatet er 240.
        assertEquals(240, capacity);
    }


    /*
     * TEST 2:
     *
     * Vi tester det vigtigste flow i TheaterService:
     *
     * Repository
     *     ↓
     * Theater
     *     ↓
     * TheaterService
     *     ↓
     * TheaterResponse
     *
     * Vi tester samtidig, at capacity bliver beregnet korrekt,
     * når Theater bliver lavet om til TheaterResponse.
     */
    @Test
    void getAllTheatersReturnsCorrectTheaterResponses() {

        // Arrange:
        // Vi laver to sale, som svarer til de sale,
        // DataInitializer opretter i vores projekt.
        Theater small = new Theater("Lille sal", 20, 12);
        Theater large = new Theater("Stor sal", 25, 16);

        /*
         * Vi fortæller vores mock repository:
         *
         * "Når TheaterService spørger efter findAll(),
         * så skal du returnere disse to sale."
         *
         * Der bliver altså ikke kontaktet en rigtig database.
         */
        when(theaterRepository.findAll())
                .thenReturn(List.of(small, large));

        // Act:
        // Vi kalder den metode, som Controlleren også bruger.
        List<TheaterResponse> result = theaterService.getAllTheaters();

        // Assert:
        // Vi forventer, at vi får to sale tilbage.
        assertEquals(2, result.size());

        // Vi tester Lille sal.
        assertEquals("Lille sal", result.get(0).name());
        assertEquals(20, result.get(0).rowCount());
        assertEquals(12, result.get(0).seatsPerRow());

        // 20 × 12 = 240
        assertEquals(240, result.get(0).capacity());

        // Vi tester Stor sal.
        assertEquals("Stor sal", result.get(1).name());
        assertEquals(25, result.get(1).rowCount());
        assertEquals(16, result.get(1).seatsPerRow());

        // 25 × 16 = 400
        assertEquals(400, result.get(1).capacity());
    }


    /*
     * TEST 3:
     *
     * Vi tester situationen, hvor databasen ikke indeholder nogen sale.
     *
     * Service-metoden skal så returnere en tom liste
     * og ikke eksempelvis null.
     */
    @Test
    void getAllTheatersReturnsEmptyListWhenNoTheatersExist() {

        // Arrange:
        // Vi fortæller mock repository, at der ikke findes nogen sale.
        when(theaterRepository.findAll())
                .thenReturn(List.of());

        // Act:
        // Vi kalder service-metoden.
        List<TheaterResponse> result = theaterService.getAllTheaters();

        // Assert:
        // Resultatet skal være en tom liste.
        assertTrue(result.isEmpty());
    }
}
