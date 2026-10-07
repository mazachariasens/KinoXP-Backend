package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}


