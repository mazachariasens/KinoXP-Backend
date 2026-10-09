package org.example.kinoxpbackend.repository;
import org.example.kinoxpbackend.entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findAllByOrderByStartsAtAsc();
}
