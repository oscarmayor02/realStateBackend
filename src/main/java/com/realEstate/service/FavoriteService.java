package com.realEstate.service;

import com.realEstate.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {
    List<FavoriteDTO> getFavoritesByUser(Long userId);
    FavoriteDTO addFavorite(Long userId, Long propertyId);
    void removeFavorite(Long userId, Long propertyId);
}