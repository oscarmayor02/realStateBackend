package com.realEstate.dto;

import com.realEstate.model.ReservationStatus;

import java.time.LocalDate;

public class ReservationRequest {
    private Long propertyId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

    public ReservationRequest() {
    }

    public ReservationRequest(Long propertyId, Long userId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.propertyId = propertyId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}