package com.realEstate.service;
import com.realEstate.model.Property;

import java.util.List;


// Interface defining operations for properties
public interface PropertyService {
    Property saveProperty(Property property); // Save a property
    List<Property> getAllAvailable(); // Get list of all available properties
    Property updateProperty(Long propertyId, Boolean newStatus);// Update property details
}