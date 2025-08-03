package com.realEstate.dto;

public class FavoriteDTO {
    private Long id;
    private Long userId;
    private Long propertyId;

    // Constructor, Getters y Setters

    public FavoriteDTO() {
    }

    public FavoriteDTO(Long id, Long userId, Long propertyId) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
