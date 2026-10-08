package org.example.kinoxpbackend.dto;

public record CreateMovieRequest(
        String title,
        String category,
        Integer ageLimit,
        Integer duration) {

}
