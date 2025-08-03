package com.realEstate.service;
import com.realEstate.dto.PropertyRequest;
import com.realEstate.model.Property;

import java.io.IOException;
import java.util.List;


// Interface defining operations for properties
public interface PropertyService {
    Property saveProperty(PropertyRequest property,  String performedBy) throws IOException; // Save a property
    List<Property> getAllAvailable(); // Get list of all available properties
    Property updateProperty(Long propertyId, Boolean newStatus);// Update property details
    List<Property> findByHostId(Long ownerId);
    public List<Property> obtenerPropiedadesDestacadas();
    public Property marcarComoDestacada(Long id);
    public Property quitarDestacado(Long id);
}