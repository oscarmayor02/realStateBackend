package com.realEstate.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "global_parameters")
public class GlobalParameter {

    @Id
    private Long id = 1L; // solo un registro único

    private Integer maxReservations;
    private Double platformFee;

    // Constructor vacío para JPA
    public GlobalParameter() {}

    // Getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxReservations() {
        return maxReservations;
    }

    public void setMaxReservations(Integer maxReservations) {
        this.maxReservations = maxReservations;
    }

    public Double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(Double platformFee) {
        this.platformFee = platformFee;
    }
}