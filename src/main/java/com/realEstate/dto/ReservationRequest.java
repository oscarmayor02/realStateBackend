package com.realEstate.dto;

import com.realEstate.model.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationRequest {
    private Long propertyId;
    private Long userId;
    private LocalDateTime  startDate;
    private LocalDateTime endDate;
    private ReservationStatus status;

    public ReservationRequest() {
    }

    public ReservationRequest(Long propertyId, Long userId, LocalDateTime  startDate, LocalDateTime  endDate, ReservationStatus status) {
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

    public LocalDateTime  getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime  startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime  getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime  endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}