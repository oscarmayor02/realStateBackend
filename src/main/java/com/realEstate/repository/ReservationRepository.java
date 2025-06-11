package com.realEstate.repository;

import com.realEstate.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository for reservation handling
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
