package org.example.kinoxpbackend.dto.request;

public record CreateMovieRequest(
        String title,
        String category,
        Integer ageLimit,
        Integer duration) {

}
