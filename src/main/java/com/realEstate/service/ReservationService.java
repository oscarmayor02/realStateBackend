package com.realEstate.service;

import com.realEstate.dto.ReservationRequest;
import com.realEstate.model.Reservation;

import java.util.List;

// Interface for managing reservations
public interface ReservationService {
    Reservation saveReservation(ReservationRequest reservation); // Save a reservation
    List<Reservation> getAllReservations(); // List all reservations
}