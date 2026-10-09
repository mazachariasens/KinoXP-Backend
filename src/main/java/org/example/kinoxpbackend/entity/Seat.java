package org.example.kinoxpbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "SEAT",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"theater_id", "row_number", "seat_number"}
                )
        }
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    public Seat() {
    }

    public Seat(Theater theater, int rowNumber, int seatNumber) {
        this.theater = theater;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    public Long getSeatId() {
        return seatId;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}