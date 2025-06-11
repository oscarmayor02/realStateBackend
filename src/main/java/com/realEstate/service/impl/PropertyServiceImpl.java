package com.realEstate.service.impl;

import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.Property;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// Service implementation for managing properties
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // Persist a property entity to the database
    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
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
}