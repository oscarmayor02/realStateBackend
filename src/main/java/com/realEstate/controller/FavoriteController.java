package com.realEstate.controller;

import com.realEstate.dto.FavoriteDTO;
import com.realEstate.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{userId}")
    public List<FavoriteDTO> getFavorites(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }

    @PostMapping
    public FavoriteDTO addFavorite(@RequestParam Long userId, @RequestParam Long propertyId) {
        return favoriteService.addFavorite(userId, propertyId);
    }

    @DeleteMapping
    public void removeFavorite(@RequestParam Long userId, @RequestParam Long propertyId) {
        favoriteService.removeFavorite(userId, propertyId);
    }
}