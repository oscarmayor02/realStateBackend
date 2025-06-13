package com.realEstate.controller;
import com.realEstate.dto.PropertyRequest;
import com.realEstate.dto.PropertyStatusDTO;
import com.realEstate.model.Property;
import com.realEstate.model.PropertyType;
import com.realEstate.service.PropertyService;
import com.realEstate.service.impl.PropertyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
// Marks this class as a Spring REST controller, handling HTTP requests and responses
@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/properties")
public class PropertyController {

    // Injects the PropertyService to handle business logic related to properties
    @Autowired
    private PropertyServiceImpl propertyService;

    // Documents the endpoint for retrieving all available properties
    @Operation(summary = "Get all available properties")
    // Maps HTTP GET requests to /api/properties/available
    @GetMapping("/available")
    public List<Property> getAvailableProperties() {
        // Returns a list of all available properties from the service
        return propertyService.getAllAvailable();
    }

    // Documents the endpoint for saving a new property
    // Maps HTTP POST requests to /api/properties
    @Operation(summary = "Save new property with images")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Property> saveProperty(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("type") String type,
            @RequestParam("price") double price,
            @RequestParam("available") boolean available,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("images") List<MultipartFile> images
    ) throws IOException {
        PropertyRequest propertyRequest = new PropertyRequest();
        propertyRequest.setTitle(title);
        propertyRequest.setDescription(description);
        propertyRequest.setLocation(location);
        propertyRequest.setType(Enum.valueOf(com.realEstate.model.PropertyType.class, type));
        propertyRequest.setPrice(price);
        propertyRequest.setAvailable(available);
        propertyRequest.setOwnerId(ownerId);
        propertyRequest.setImages(images);

        Property saved = propertyService.saveProperty(propertyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    // Documents the endpoint for updating an existing property
    @Operation(summary = "Update property")
    // Maps HTTP PUT requests to /api/properties
    @PutMapping("/{id}/availability")
    public ResponseEntity<Property> updatePropertyAvailability(
            @PathVariable Long id,
            @RequestBody @Valid PropertyStatusDTO statusDTO) {

        Property updated = propertyService.updateProperty(id, statusDTO.getAvailable());
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/filter")
    public List<Property> filterProperties(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean available
    ) {
        return propertyService.filterProperties(location, type, minPrice, maxPrice, available);
    }
}
