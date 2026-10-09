
package org.example.kinoxpbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "RESERVED_SEAT",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"showing_id", "seat_id"}
                )
        }
)
public class ReservedSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservedSeatId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "showing_id", nullable = false)
    private Showing showing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    public ReservedSeat() {
    }

    public ReservedSeat(Showing showing, Seat seat) {
        this.showing = showing;
        this.seat = seat;
    }

    public Long getReservedSeatId() {
        return reservedSeatId;
    }

    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
