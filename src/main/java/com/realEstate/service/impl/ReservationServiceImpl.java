package com.realEstate.service.impl;

import com.realEstate.dto.ReservationRequest;
import com.realEstate.model.Property;
import com.realEstate.model.Reservation;
import com.realEstate.model.User;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.ReservationRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service implementation for reservation logic
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    // Save or update reservation
    @Override
    public Reservation saveReservation(ReservationRequest request) {

        // Buscar entidades relacionadas
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        // Crear y llenar la reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setProperty(property);
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setStatus(request.getStatus()); // o la que sea

        return reservationRepository.save(reservation);
    }

    // Retrieve all reservations
    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}