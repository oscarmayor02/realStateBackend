package com.realEstate.controller;
import com.realEstate.dto.PropertyStatusDTO;
import com.realEstate.model.Property;
import com.realEstate.service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Marks this class as a Spring REST controller, handling HTTP requests and responses
@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/properties")
public class PropertyController {

    // Injects the PropertyService to handle business logic related to properties
    @Autowired
    private PropertyService propertyService;

    // Documents the endpoint for retrieving all available properties
    @Operation(summary = "Get all available properties")
    // Maps HTTP GET requests to /api/properties/available
    @GetMapping("/available")
    public List<Property> getAvailableProperties() {
        // Returns a list of all available properties from the service
        return propertyService.getAllAvailable();
    }

    // Documents the endpoint for saving a new property
    @Operation(summary = "Save new property")
    // Maps HTTP POST requests to /api/properties
    @PostMapping
    public Property saveProperty(@RequestBody Property property) {
        // Saves the property received in the request body and returns it
        return propertyService.saveProperty(property);
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

}
