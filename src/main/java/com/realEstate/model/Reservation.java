package com.realEstate.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")

public class Reservation {

    // Unique reservation ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Start date of the reservation
    private LocalDate startDate;

    // End date of the reservation
    private LocalDate endDate;

    // User making the reservation
    @ManyToOne
    private User user;

    // Reserved property
    @ManyToOne
    private Property property;

    // Status of the reservation
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}