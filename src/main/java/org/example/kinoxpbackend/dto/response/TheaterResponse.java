package org.example.kinoxpbackend.dto.response;
//DTO (Data Transfer Object) for Theater Response. This class is used to transfer data from the backend to the frontend in a structured format.
//Et Java-objekt som bruges til at strukturere de data, API'et skal sende tilbage.
public record TheaterResponse(
        Long theaterId,
        String name,
        int rowCount,
        int seatsPerRow,
        int capacity
) {
}

//Det betyder, at API'et kan returnere et objekt af typen TheaterResponse, som indeholder:
//{ "theaterID": 1,
// "name": "Lille sal",
// "rowCount": 20,
// "seatsPerRow": 12,
// "capacity": 240
//}
// JSON-objekt er en repræsentation af et objekt, der kan sendes som svar fra API'et.

// Når Spring Boot/Jackson sender det over HTTP, bliver Java-objeket serialiseret til JSON-format, som kan forstås af klienten (f.eks. en webbrowser)
