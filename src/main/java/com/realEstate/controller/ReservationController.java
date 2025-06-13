package com.realEstate.controller;
import com.realEstate.dto.ReservationRequest;
import com.realEstate.model.Reservation;
import com.realEstate.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks this class as a Spring REST controller, handling HTTP requests and responses
@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/reservations")
public class ReservationController {

    // Injects the ReservationService to handle business logic related to reservations
    @Autowired
    private ReservationService reservationService;

    // Documents the endpoint for retrieving all reservations
    @Operation(summary = "Get all reservations")
    // Maps HTTP GET requests to /api/reservations
    @GetMapping
    public List<Reservation> getAll() {
        // Returns a list of all reservations from the service
        return reservationService.getAllReservations();
    }

    // Documents the endpoint for saving a new reservation
    @Operation(summary = "Save a reservation")
    // Maps HTTP POST requests to /api/reservations
    @PostMapping
    public Reservation save(@RequestBody ReservationRequest reservation) {
        // Saves the reservation received in the request body and returns it
        return reservationService.saveReservation(reservation);
    }
}