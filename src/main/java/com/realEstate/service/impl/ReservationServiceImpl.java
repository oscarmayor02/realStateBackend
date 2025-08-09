package com.realEstate.service.impl;

import com.realEstate.dto.ReservationRequest;
import com.realEstate.model.*;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.ReservationRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.AdminLogService;
import com.realEstate.service.GlobalParameterService;
import com.realEstate.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// Service implementation for reservation logic
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private AdminLogService logService;

    @Autowired
    private GlobalParameterService parameterService;

    @Autowired
    private EmailServiceImpl emailServiceImpl; // Injects the email service dependency
    // Save or update reservation
    @Override
    public Reservation saveReservation(ReservationRequest request) {

        // Buscar entidades relacionadas
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
// Obtener parámetros globales
        GlobalParameter params = parameterService.getParameters();

        // Validar límite de reservas activas del usuario
        long activeReservations = reservationRepository.countByUser_IdAndStatusIn(
                user.getId(), List.of(ReservationStatus.PENDING, ReservationStatus.CONFIRMED)
        );
        if (activeReservations >= params.getMaxReservations()) {
            throw new RuntimeException("Has alcanzado el límite máximo de reservas permitidas.");
        }
        // Crear y llenar la reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setProperty(property);
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setStatus(ReservationStatus.PENDING); // Estado inicial pendiente

        Property propiedad = reservation.getProperty();
        User host = propiedad.getHost();
        User cliente = reservation.getUser();


        // Cargar el template HTML y enviar correo
        try {
            Map<String, String> variables = Map.of("nombre", cliente.getName());
            String contenidoHtml = emailServiceImpl.cargarTemplate("reservation-notify-host.html", variables);

            emailServiceImpl.enviarCorreoHtml(
                    host.getEmail(),
                    "¡Te han reservado una propiedad en UbikkApp!",
                    contenidoHtml
            );
        } catch (IOException e) {
            e.printStackTrace();
            // Puedes decidir si lanzar excepción o solo loguear el error aquí
        }
        return reservationRepository.save(reservation);
    }

    // Retrieve all reservations
    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation updateStatus(Long id, ReservationStatus status, String performedBy) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reservation.setStatus(status);
        logService.logAction(
                "Actualización de reserva",
                performedBy,
                "Reserva con ID " + id + " fue " + (status.equals("CONFIRMED") ? "confirmed" : "rechazada")
        );
        User cliente = reservation.getUser();
        String estado = reservation.getStatus().name();

        String mensaje = estado.equals("CONFIRMED") ?
                " ACEPTAD0 " :
                " RECHAZADO ";

        // Cargar el template HTML y enviar correo
        try {
            Map<String, String> variables = Map.of("nombre", cliente.getName(), "estado", mensaje);
            String contenidoHtml = emailServiceImpl.cargarTemplate("reservation-response-client.html", variables);

            emailServiceImpl.enviarCorreoHtml(
                    cliente.getEmail(),
                    "¡Te han actualizado la reserva en UbikkApp!",
                    contenidoHtml
            );
        } catch (IOException e) {
            e.printStackTrace();
            // Puedes decidir si lanzar excepción o solo loguear el error aquí
        }


        return reservationRepository.save(reservation);

    }

    @Override
    public List<Reservation> findByUser_Id(Long clientId) {
        return reservationRepository.findByUser_Id(clientId);
    }

    @Override
    public List<Reservation> findByProperty_Host_Id(Long hostId) {
        return reservationRepository.findByProperty_Host_Id(hostId);
    }
}