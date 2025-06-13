package com.realEstate.service.impl;

import com.realEstate.dto.PropertyRequest;
import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.Image;
import com.realEstate.model.Property;
import com.realEstate.model.PropertyType;
import com.realEstate.model.User;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Service implementation for managing properties
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserRepository userRepository;

    // Persist a property entity to the database
    @Override
    public Property saveProperty(PropertyRequest request) throws IOException {
        // Uploads the image from the request to Cloudinary and gets the image URL
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setLocation(request.getLocation());
        property.setType(request.getType());
        property.setPrice(request.getPrice());
        property.setAvailable(request.isAvailable());

        // Buscar owner
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        property.setOwner(owner);


        List<Image> images = new ArrayList<>();
        for (MultipartFile file : request.getImages()) {
            String imageUrl = cloudinaryService.uploadImage(file); // 👈 Aquí se llama
            Image img = new Image();
            img.setUrl(imageUrl);
            img.setProperty(property); // vínculo bidireccional
            images.add(img);
        }

        property.setImages(images);
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

    public List<Property> filterProperties(String location, PropertyType type,
                                           Double minPrice, Double maxPrice, Boolean available) {
        return propertyRepository.filterProperties(location, type, minPrice, maxPrice, available);
    }
}