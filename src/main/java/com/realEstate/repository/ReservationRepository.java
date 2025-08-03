package com.realEstate.repository;

import com.realEstate.model.Reservation;
import com.realEstate.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository for reservation handling
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByProperty_Host_Id(Long hostId);
    List<Reservation> findByUser_Id(Long userId);
    void deleteByProperty_Id(Long propertyId); // <- Agrega este método
    long countByUser_IdAndStatusIn(Long userId, List<ReservationStatus> statuses);

}
