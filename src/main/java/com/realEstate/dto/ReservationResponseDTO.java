package com.realEstate.dto;

import com.realEstate.model.ReservationStatus;

import java.time.LocalDateTime;

public class ReservationResponseDTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
    private String clientName;
    private Long propertyId;
    private String propertyTitle;
    private ReservationStatus status;
    private String propertyAddress; // <-- nuevo
    private Long hostId;

    public ReservationResponseDTO() {}

    public ReservationResponseDTO(Long id, LocalDateTime startDate, LocalDateTime endDate,
                                  Long userId, String clientName,
                                  String propertyTitle,
                                  ReservationStatus status,Long hostId, String propertyAddress) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.clientName = clientName;
        this.propertyTitle = propertyTitle;
        this.propertyAddress = propertyAddress;
        this.status = status;
        this.hostId = hostId;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
}
