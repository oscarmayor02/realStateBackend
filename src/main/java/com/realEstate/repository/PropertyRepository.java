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
    List<Property> findByDestacadoTrue();

    @Query("SELECT p FROM Property p WHERE " +
            "(:departamento IS NULL OR p.departamento = :departamento) AND " +
            "(:ciudad IS NULL OR p.ciudad = :ciudad) AND " +
            "(:type IS NULL OR p.type = :type) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND " + // 👈 nuevo filtro
            "(:available IS NULL OR p.available = :available)")
    List<Property> filterProperties(
            @Param("departamento") String departamento,
            @Param("ciudad") String ciudad,
            @Param("type") PropertyType type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("bedrooms") Integer bedrooms,
            @Param("available") Boolean available
    );
    List<Property> findByHostId(Long ownerId);

}