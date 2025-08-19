package com.realEstate.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realEstate.dto.*;
import com.realEstate.model.Property;
import com.realEstate.model.OperationType;
import com.realEstate.model.PropertyCategory;
import com.realEstate.service.impl.PropertyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyServiceImpl propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Operation(summary = "Get all available properties")
    @GetMapping("/available")
    public List<PropertyResponse> getAvailableProperties() {
        List<Property> properties = propertyService.getAllAvailable();
        return properties.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Operation(summary = "Save new property with images")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<PropertyResponse> saveProperty(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("departamento") String departamento,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "bedrooms" , required = false) Integer bedrooms,
            @RequestParam(value ="bathrooms", required = false) Integer bathrooms,
            @RequestParam(value = "area", required = false) Double area,
            @RequestParam("address") String address,
            @RequestParam(value = "yearBuilt", required = false) Integer yearBuilt,
            @RequestParam("operationType") String operationType,
            @RequestParam(value = "propertyCategory", required = false) String propertyCategory,
            @RequestParam(value ="parkingSpaces", required = false) boolean parkingSpaces,
            @RequestParam(value = "estrato", required = false) Integer estrato,
            @RequestParam(value ="petsAllowed", required = false) Boolean petsAllowed,
            @RequestParam(value="balcony", required = false) Boolean balcony,
            @RequestParam(value= "terrace", required = false) Boolean terrace,
            @RequestParam(value="pool", required = false) Boolean pool,
            @RequestParam(value = "price", required = false) double price,
            @RequestParam(value = "available", required = false) boolean available,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(value = "availabilityList", required = false) String availabilityListJson,
            @AuthenticationPrincipal UserDetails adminUser
    ) throws IOException {

        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setTitle(title);
        propertyRequest.setDescription(description);
        propertyRequest.setDepartamento(departamento);
        propertyRequest.setCiudad(ciudad);
        propertyRequest.setLatitude(latitude);
        propertyRequest.setLongitude(longitude);
        propertyRequest.setBedrooms(bedrooms);
        propertyRequest.setBathrooms(bathrooms);
        propertyRequest.setArea(area);
        propertyRequest.setAddress(address);
        propertyRequest.setYearBuilt(yearBuilt);
        propertyRequest.setOperationType(Enum.valueOf(OperationType.class, operationType.toUpperCase()));
        propertyRequest.setPropertyCategory(Enum.valueOf(PropertyCategory.class,propertyCategory.toUpperCase()));
        propertyRequest.setParkingSpaces(parkingSpaces);
        propertyRequest.setEstrato(estrato);
        propertyRequest.setPetsAllowed(petsAllowed);
        propertyRequest.setBalcony(balcony);
        propertyRequest.setTerrace(terrace);
        propertyRequest.setPool(pool);
        propertyRequest.setPrice(price);
        propertyRequest.setAvailable(available);
        propertyRequest.setOwnerId(ownerId);
        propertyRequest.setImages(images);

        if (availabilityListJson != null) {
            List<AvailabilityDTO> availabilityList = objectMapper.readValue(
                    availabilityListJson,
                    new TypeReference<List<AvailabilityDTO>>() {}
            );
            propertyRequest.setAvailabilityList(availabilityList);
        }

        String adminEmail = adminUser.getUsername();
        Property saved = propertyService.saveProperty(propertyRequest, adminEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(saved));
    }

    @Operation(summary = "Update property availability status")
    @PutMapping("/{id}/availability")
    public ResponseEntity<PropertyResponse> updatePropertyAvailability(
            @PathVariable Long id,
            @RequestBody @Valid PropertyStatusDTO statusDTO) {

        Property updated = propertyService.updateProperty(id, statusDTO.getAvailable());
        return ResponseEntity.ok(mapToResponse(updated));
    }

    @Operation(summary = "Update property with images")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("departamento") String departamento,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "bedrooms", required = false) Integer bedrooms,
            @RequestParam(value = "bathrooms", required = false) Integer bathrooms,
            @RequestParam(value = "area", required = false) Double area,
            @RequestParam("address") String address,
            @RequestParam(value = "yearBuilt", required = false) Integer yearBuilt,
            @RequestParam("operationType") String operationType,
            @RequestParam(value = "propertyCategory", required = false) String propertyCategory,
            @RequestParam(value = "parkingSpaces", required = false) boolean parkingSpaces,
            @RequestParam(value = "estrato", required = false) Integer estrato,
            @RequestParam(value = "petsAllowed", required = false) Boolean petsAllowed,
            @RequestParam(value = "balcony", required = false) Boolean balcony,
            @RequestParam(value = "terrace", required = false) Boolean terrace,
            @RequestParam(value = "pool", required = false) Boolean pool,
            @RequestParam(value = "price", required = false) double price,
            @RequestParam(value = "available", required = false) boolean available,
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "availabilityList", required = false) String availabilityListJson
    ) throws IOException {

        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setTitle(title);
        propertyRequest.setDescription(description);
        propertyRequest.setDepartamento(departamento);
        propertyRequest.setCiudad(ciudad);
        propertyRequest.setLatitude(latitude);
        propertyRequest.setLongitude(longitude);
        propertyRequest.setBedrooms(bedrooms);
        propertyRequest.setBathrooms(bathrooms);
        propertyRequest.setArea(area);
        propertyRequest.setAddress(address);
        propertyRequest.setYearBuilt(yearBuilt);
        propertyRequest.setOperationType(Enum.valueOf(OperationType.class, operationType.toUpperCase()));
        propertyRequest.setPropertyCategory(Enum.valueOf(PropertyCategory.class,propertyCategory.toUpperCase()));
        propertyRequest.setParkingSpaces(parkingSpaces);
        propertyRequest.setEstrato(estrato);
        propertyRequest.setPetsAllowed(petsAllowed);
        propertyRequest.setBalcony(balcony);
        propertyRequest.setTerrace(terrace);
        propertyRequest.setPool(pool);
        propertyRequest.setPrice(price);
        propertyRequest.setAvailable(available);
        propertyRequest.setOwnerId(ownerId);
        propertyRequest.setImages(images);

        if (availabilityListJson != null) {
            List<AvailabilityDTO> availabilityList = objectMapper.readValue(
                    availabilityListJson,
                    new TypeReference<List<AvailabilityDTO>>() {}
            );
            propertyRequest.setAvailabilityList(availabilityList);
        }

        Property updated = propertyService.updatePropertyWithDetails(id, propertyRequest);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    @GetMapping("/filter")
    public List<PropertyResponse> filterProperties(
            @RequestParam(required = false) String departamento,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) OperationType type,
            @RequestParam(required = false) PropertyCategory category, // CASA / APARTAMENTO / LOTE
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer bedrooms, // 👈 nuevo filtro
            @RequestParam(required = false) Boolean available
    ) {
        List<Property> properties = propertyService.filterProperties(
                departamento, ciudad, type,category, minPrice, maxPrice, bedrooms, available
        );
        return properties.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Operation(summary = "Get properties by owner ID")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PropertyResponse>> getPropertiesByOwner(@PathVariable Long ownerId) {
        List<Property> properties = propertyService.findByHostId(ownerId);
        List<PropertyResponse> responses = properties.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get property by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getById(id);
        return ResponseEntity.ok(mapToResponse(property));
    }

    @Operation(summary = "Delete property by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/destacadas")
    public ResponseEntity<List<Property>> obtenerDestacadas() {
        return ResponseEntity.ok(propertyService.obtenerPropiedadesDestacadas());
    }

    @PostMapping("/{id}/destacar")
    public ResponseEntity<Property> destacar(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.marcarComoDestacada(id));
    }

    @PostMapping("/{id}/quitar-destacado")
    public ResponseEntity<Property> quitarDestacado(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.quitarDestacado(id));
    }

    // Método para mapear Property a PropertyResponse (DTO plano)
    private PropertyResponse mapToResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setTitle(property.getTitle());
        response.setDescription(property.getDescription());
        response.setDepartamento(property.getDepartamento());
        response.setCiudad(property.getCiudad());
        response.setLatitude(property.getLatitude());
        response.setLongitude(property.getLongitude());
        response.setBedrooms(property.getBedrooms());
        response.setBathrooms(property.getBathrooms());
        response.setArea(property.getArea());
        response.setAddress(property.getAddress());
        response.setYearBuilt(property.getYearBuilt());
        response.setOperationType(Enum.valueOf(OperationType.class, property.getOperationType().name()));
        response.setPropertyTypeName(property.getPropertyTypeName());
        response.setParkingSpaces(property.getParkingSpaces());
        response.setEstrato(property.getEstrato());
        response.setPetsAllowed(property.getPetsAllowed());
        response.setBalcony(property.getBalcony());
        response.setTerrace(property.getTerrace());
        response.setPool(property.getPool());
        response.setPrice(property.getPrice());
        response.setAvailable(property.getAvailable());
        response.setCreatedAt(property.getCreatedAt());  // <--- Aquí agregas la fecha de creación
        response.setDestacado(property.isDestacado());

        // NUEVO: mapear el host a un DTO
        if (property.getHost() != null) {
            UserDTO hostDTO = new UserDTO();
            hostDTO.setId(property.getHost().getId());
            hostDTO.setName(property.getHost().getName());
            hostDTO.setEmail(property.getHost().getEmail());
            hostDTO.setCreatedAt(property.getHost().getCreatedAt());
            response.setHost(hostDTO);
        }

        if (property.getAvailabilityList() != null) {
            List<AvailabilityDTO> availabilityDTOs = property.getAvailabilityList().stream()
                    .map(a -> {
                        AvailabilityDTO dto = new AvailabilityDTO();
                        dto.setDayOfWeek(a.getDayOfWeek());
                        dto.setStartTime(a.getStartTime());
                        dto.setEndTime(a.getEndTime());

                        return dto;
                    }).collect(Collectors.toList());
            response.setAvailabilityList(availabilityDTOs);
        }
        List<Map<String, String>> imageObjects = property.getImages().stream()
                .map(image -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("url", image.getUrl());
                    return map;
                })
                .collect(Collectors.toList());
        response.setImages(imageObjects);

        return response;
    }
}
