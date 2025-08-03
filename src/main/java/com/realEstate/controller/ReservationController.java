package com.realEstate.controller;

import com.realEstate.dto.ReservationRequest;
import com.realEstate.dto.ReservationResponseDTO;
import com.realEstate.model.Reservation;
import com.realEstate.model.ReservationStatus;
import com.realEstate.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping
    public List<ReservationResponseDTO> getAll() {
        return reservationService.getAllReservations().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Crear una nueva reserva")
    @PostMapping
    public ReservationResponseDTO save(@RequestBody ReservationRequest reservation) {
        Reservation saved = reservationService.saveReservation(reservation);
        return convertToDTO(saved);
    }

    @Operation(summary = "Actualizar el estado de una reserva")
    @PatchMapping("/{id}/status")
    public ReservationResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status,  @AuthenticationPrincipal UserDetails adminUser
    ) {
        String adminEmail = adminUser.getUsername();
        Reservation updated = reservationService.updateStatus(id, status,adminEmail);
        return convertToDTO(updated);
    }

    @Operation(summary = "Obtener reservas recibidas por el host")
    @GetMapping("/host/{hostId}")
    public List<ReservationResponseDTO> getReservationsByHost(@PathVariable Long hostId) {
        List<Reservation> reservations = reservationService.findByProperty_Host_Id(hostId);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener reservas realizadas por el cliente")
    @GetMapping("/client/{clientId}")
    public List<ReservationResponseDTO> getReservationsByClient(@PathVariable Long clientId) {
        List<Reservation> reservations = reservationService.findByUser_Id(clientId);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private ReservationResponseDTO convertToDTO(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getUser().getId(),
                reservation.getUser().getName(), // suponiendo que existe un getName()
                reservation.getProperty().getTitle(), // suponiendo que existe un getTitle()
                reservation.getStatus(),
                reservation.getProperty().getHost().getId(),
                reservation.getProperty().getAddress() // <-- aquí

        );
    }
}
