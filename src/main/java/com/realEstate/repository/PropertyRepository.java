package com.realEstate.repository;
import com.realEstate.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository for handling property-related DB operations
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAvailable(Boolean available);
}