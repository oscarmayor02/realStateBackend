package com.realEstate.dto;

import jakarta.validation.constraints.NotNull;

public class PropertyStatusDTO {
    @NotNull(message = "The availability status is required.")
    private Boolean available;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
