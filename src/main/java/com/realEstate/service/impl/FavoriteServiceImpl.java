package com.realEstate.service.impl;

import com.realEstate.dto.FavoriteDTO;
import com.realEstate.model.Favorite;
import com.realEstate.repository.FavoriteRepository;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.FavoriteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<FavoriteDTO> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(fav -> new FavoriteDTO(fav.getId(), fav.getUser().getId(), fav.getProperty().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public FavoriteDTO addFavorite(Long userId, Long propertyId) {
        if (favoriteRepository.findByUserIdAndPropertyId(userId, propertyId).isPresent()) {
            throw new IllegalStateException("Ya está en favoritos");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(userRepository.findById(userId).orElseThrow());
        favorite.setProperty(propertyRepository.findById(propertyId).orElseThrow());

        Favorite saved = favoriteRepository.save(favorite);
        return new FavoriteDTO(saved.getId(), userId, propertyId);
    }

    @Override
    @Transactional  // ← ESTA LÍNEA ES CLAVE
    public void removeFavorite(Long userId, Long propertyId) {
        Favorite favorite = favoriteRepository.findByUserIdAndPropertyId(userId, propertyId)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }


}