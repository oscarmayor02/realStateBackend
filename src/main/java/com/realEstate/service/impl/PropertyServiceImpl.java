package com.realEstate.service.impl;

import com.realEstate.dto.AvailabilityDTO;
import com.realEstate.dto.PropertyRequest;
import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.*;
import com.realEstate.repository.FavoriteRepository;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.ReservationRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.AdminLogService;
import com.realEstate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Service implementation for managing properties
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private AdminLogService logService;

    // Persist a property entity to the database
    @Override
    public Property saveProperty(PropertyRequest request, String performedBy) throws IOException {
        // Uploads the image from the request to Cloudinary and gets the image URL
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setDepartamento(request.getDepartamento());
        property.setCiudad(request.getCiudad());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setArea(request.getArea());
        property.setAddress(request.getAddress());
        property.setYearBuilt(request.getYearBuilt());
        property.setOperationType(request.getOperationType());
        property.setPropertyCategory(request.getPropertyCategory());
        property.setParkingSpaces(request.isParkingSpaces());
        property.setEstrato(request.getEstrato());
        property.setPetsAllowed(request.getPetsAllowed());
        property.setBalcony(request.getBalcony());
        property.setTerrace(request.getTerrace());
        property.setPool(request.getPool());
        property.setPrice(request.getPrice());
        property.setAvailable(request.isAvailable());
        property.setDestacado(request.isDestacado());
// Disponibilidad
        if (request.getAvailabilityList() != null) {
            List<Availability> availabilityEntities = new ArrayList<>();
            for (AvailabilityDTO dto : request.getAvailabilityList()) {
                Availability availability = new Availability();
                availability.setDayOfWeek(dto.getDayOfWeek());
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
                availability.setProperty(property); // vínculo
                availabilityEntities.add(availability);
            }
            property.setAvailabilityList(availabilityEntities);
        }


        // Buscar owner
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        property.setHost(owner);


        List<Image> images = new ArrayList<>();
        for (MultipartFile file : request.getImages()) {
            String imageUrl = cloudinaryService.uploadImage(file); // 👈 Aquí se llama
            Image img = new Image();
            img.setUrl(imageUrl);
            img.setProperty(property); // vínculo bidireccional
            images.add(img);
        }

        property.setImages(images);

        Property savedProperty = propertyRepository.save(property);


        logService.logAction(
                "Creación de propiedad",
                performedBy,
                "Propiedad creada con ID " + savedProperty.getId()
        );

        return savedProperty;
    }


    // Retrieve all properties marked as available
    @Override
    public List<Property> getAllAvailable() {
        return propertyRepository.findByAvailable(true);
    }

    // Update an existing property (e.g., change status to sold or rented)
    @Override
    public Property updateProperty(Long propertyId, Boolean newStatus) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + propertyId));

        property.setAvailable(newStatus);
        return propertyRepository.save(property); // save() acts as update if ID exists
    }

    public Property updatePropertyWithDetails(Long id, PropertyRequest request) throws IOException {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con ID: " + id));

        // Actualizar campos
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setDepartamento(request.getDepartamento());
        property.setCiudad(request.getCiudad());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setArea(request.getArea());
        property.setAddress(request.getAddress());
        property.setYearBuilt(request.getYearBuilt());
        property.setOperationType(request.getOperationType());
        property.setPropertyCategory(request.getPropertyCategory());
        property.setParkingSpaces(request.isParkingSpaces());
        property.setEstrato(request.getEstrato());
        property.setPetsAllowed(request.getPetsAllowed());
        property.setBalcony(request.getBalcony());
        property.setTerrace(request.getTerrace());
        property.setPool(request.getPool());
        property.setPrice(request.getPrice());
        property.setDestacado(request.isDestacado());

        // Disponibilidad
        if (request.getAvailabilityList() != null) {
            List<Availability> availabilityEntities = new ArrayList<>();
            for (AvailabilityDTO dto : request.getAvailabilityList()) {
                Availability availability = new Availability();
                availability.setDayOfWeek(dto.getDayOfWeek());
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
                availability.setProperty(property); // vínculo
                availabilityEntities.add(availability);
            }
            property.setAvailabilityList(availabilityEntities);
        }

        // Actualizar owner
        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getOwnerId()));
            property.setHost(owner);
        }

        // Actualizar imágenes si se enviaron nuevas
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<Image> currentImages = property.getImages();
            currentImages.clear(); // Marca como huérfanas

            for (MultipartFile file : request.getImages()) {
                String imageUrl = cloudinaryService.uploadImage(file);
                Image img = new Image();
                img.setUrl(imageUrl);
                img.setProperty(property); // Relación inversa
                currentImages.add(img);   // Se mantiene la colección original
            }
        }

        return propertyRepository.save(property);
    }


    @Override
    public List<Property> findByHostId(Long ownerId) {
        return propertyRepository.findByHostId(ownerId);

    }

    public List<Property> filterProperties(
            String departamento,
            String ciudad,
            OperationType type,
            PropertyCategory category,
            Double minPrice,
            Double maxPrice,
            Integer bedrooms,
            Boolean available
    ) {
        return propertyRepository.filterProperties(
                departamento, ciudad, type, category, minPrice, maxPrice, bedrooms, available
        );
    }

    public Property getById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada con ID: " + id));
    }

    @Transactional
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Property not found");
        }
        favoriteRepository.deleteByPropertyId(id);
        reservationRepository.deleteByProperty_Id(id); // Elimina reservas asociadas
        propertyRepository.deleteById(id);
    }


    @Override
    public List<Property> obtenerPropiedadesDestacadas() {
        return propertyRepository.findByDestacadoTrue();
    }

    @Override
    public Property marcarComoDestacada(Long id) {
        Optional<Property> propiedad = propertyRepository.findById(id);
        if (propiedad.isPresent()) {
            Property p = propiedad.get();
            p.setDestacado(true);
            return propertyRepository.save(p);
        } else {
            throw new RuntimeException("Propiedad no encontrada");
        }
    }

    @Override
    public Property quitarDestacado(Long id) {
        Optional<Property> propiedad = propertyRepository.findById(id);
        if (propiedad.isPresent()) {
            Property p = propiedad.get();
            p.setDestacado(false);
            return propertyRepository.save(p);
        } else {
            throw new RuntimeException("Propiedad no encontrada");
        }
    }

}