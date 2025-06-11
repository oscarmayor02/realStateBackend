package com.realEstate.service.impl;

import com.realEstate.model.Reservation;
import com.realEstate.repository.ReservationRepository;
import com.realEstate.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service implementation for reservation logic
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Save or update reservation
    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Retrieve all reservations
    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}