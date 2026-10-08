package org.example.kinoxpbackend.entity;

import jakarta.persistence.*;

@Entity // This annotation specifies that the class is an entity and is mapped to a database table
@Table(name = "THEATER")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId; // Primary key for the Theater entity

    private String name;
    private int rowCount;
    private int seatsPerRow;

    public Theater() { // Default constructor required by JPA
    }

    public Theater(String name, int rowCount, int seatsPerRow) {
        this.name = name;
        this.rowCount = rowCount;
        this.seatsPerRow = seatsPerRow;
    }

    public Long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Long theaterId) {
        this.theaterId = theaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getSeatsPerRow() { return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

}
