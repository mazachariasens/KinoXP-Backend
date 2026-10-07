package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> { // JpaRepository provides crud operations for the Theater entity

}
