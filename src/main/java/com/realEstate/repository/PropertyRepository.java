package com.realEstate.repository;
import com.realEstate.model.Property;
import com.realEstate.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Repository for handling property-related DB operations
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAvailable(Boolean available);

    @Query("SELECT p FROM Property p WHERE " +
            "(:location IS NULL OR p.location = :location) AND " +
            "(:type IS NULL OR p.type = :type) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:available IS NULL OR p.available = :available)")
    List<Property> filterProperties(@Param("location") String location,
                                    @Param("type") PropertyType type,
                                    @Param("minPrice") Double minPrice,
                                    @Param("maxPrice") Double maxPrice,
                                    @Param("available") Boolean available);
}