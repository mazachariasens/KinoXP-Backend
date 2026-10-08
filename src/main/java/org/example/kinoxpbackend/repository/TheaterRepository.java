package org.example.kinoxpbackend.repository;

import org.example.kinoxpbackend.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
//Spring data repository - The JPA repository provides CRUD operations for the Theater entity. It extends JpaRepository, which is a Spring Data interface that provides methods for interacting with the database.
public interface TheaterRepository extends JpaRepository<Theater, Long> { // JpaRepository provides crud operations for the Theater entity

}
