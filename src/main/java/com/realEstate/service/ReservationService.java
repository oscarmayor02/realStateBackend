package com.realEstate.service;

import com.realEstate.dto.ReservationRequest;
import com.realEstate.model.Reservation;
import com.realEstate.model.ReservationStatus;

import java.util.List;

// Interface for managing reservations
public interface ReservationService {
    Reservation saveReservation(ReservationRequest reservation);
    List<Reservation> getAllReservations();
    Reservation updateStatus(Long id, ReservationStatus status,String performedBy);
    List<Reservation> findByProperty_Host_Id(Long hostId);
    List<Reservation> findByUser_Id(Long clientId);

}