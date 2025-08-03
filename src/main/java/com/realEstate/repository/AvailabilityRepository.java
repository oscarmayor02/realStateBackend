package com.realEstate.repository;

import com.realEstate.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByPropertyId(Long propertyId);
}