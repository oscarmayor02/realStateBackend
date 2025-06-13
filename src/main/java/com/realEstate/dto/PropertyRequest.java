package com.realEstate.dto;

import com.realEstate.model.Image;
import com.realEstate.model.PropertyType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PropertyRequest {
    private String title;
    private String description;
    private String location;
    private PropertyType type;
    private double price;
    private boolean available;
    private Long ownerId; // solo el ID del propietario
    private List<MultipartFile> images; // 👈 aquí debe ser lista // imagen que se subirá a Cloudinary

    public PropertyRequest() {
    }

    public PropertyRequest(String title, List<MultipartFile> images, Long ownerId, boolean available, double price, PropertyType type, String location, String description) {
        this.title = title;
        this.images = images;
        this.ownerId = ownerId;
        this.available = available;
        this.price = price;
        this.type = type;
        this.location = location;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
