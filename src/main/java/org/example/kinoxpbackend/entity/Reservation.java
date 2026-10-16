package org.example.kinoxpbackend.entity;


import jakarta.persistence.*;
import org.example.kinoxpbackend.enums.ReservationStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity // fortæller JPA, at klassen skal mappes til en databaseentitet.
@Table(name = "RESERVATION") // angiver tabellens navn i databasen.
public class Reservation {


    @Id // angiver, at feltet er primærnøglen for entiteten.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'et genereres automatisk af databasen.
    @Column(name = "reservation_id") //angiver kolonnenavnet i databasen for dette felt.
    private Long reservationId; //Vi bruger Long fordi ID'et endnu ikke eksisterer, før reservationen er oprettet, det kan derfor være null inden lagringen.

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING) // status gemmes som tekst i databasen.
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Mange reservationer kan tilhøre den samme showing.
    @JoinColumn(name = "showing_id", nullable = false) // Reservations tabellen indeholder en foreign key kolonne "showing_id" der refererer til Showing entiteten.
    private Showing showing;

    public Reservation() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {this.customerPhone = customerPhone;}

    public String getCustomerPhone() {return customerPhone; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Showing getShowing() {
        return showing;
    }

    public void setShowing(Showing showing) {
        this.showing = showing;
    }
}
