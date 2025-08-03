package com.realEstate.repository;

import com.realEstate.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndPropertyId(Long userId, Long propertyId);
    void deleteByUserIdAndPropertyId(Long userId, Long propertyId);
    void deleteByPropertyId(Long propertyId); // <- Este método elimina todos los favoritos de una propiedad

}

