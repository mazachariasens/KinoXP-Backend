
package org.example.kinoxpbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RESERVED_SEAT")
public class ReservedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserved_seat_id")
    private Long reservedSeatId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    public ReservedSeat() {
    }

    public ReservedSeat(Reservation reservation, Seat seat) {
        this.reservation = reservation;
        this.seat = seat;
    }

    public Long getReservedSeatId() {
        return reservedSeatId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
