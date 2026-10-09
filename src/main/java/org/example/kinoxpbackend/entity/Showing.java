package org.example.kinoxpbackend.entity;
import org.example.kinoxpbackend.enums.ShowingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "SHOWING")
public class Showing {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "showing_id")
    private Long showingId;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShowingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id",
            nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", referencedColumnName = "theaterId",
            nullable = false)
    private Theater theater;

    public Showing() {
    }

    public Long getShowingId() {
        return showingId;
    }

    public void setShowingId(Long showingId) {
        this.showingId = showingId;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public ShowingStatus getStatus() {
        return status;
    }

    public void setStatus(ShowingStatus status) {
        this.status = status;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}


