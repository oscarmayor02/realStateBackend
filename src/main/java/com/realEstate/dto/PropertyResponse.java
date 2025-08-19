package com.realEstate.dto;

import com.realEstate.model.Image;
import com.realEstate.model.OperationType;
import com.realEstate.model.PropertyCategory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String departamento;
    private String ciudad;
    private Double latitude;
    private Double longitude;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private String address;
    private Integer yearBuilt;
    private OperationType operationType;
    private PropertyCategory propertyTypeName;
    private boolean parkingSpaces;
    private Integer estrato;
    private Boolean petsAllowed;
    private Boolean balcony;
    private Boolean terrace;
    private Boolean pool;
    private double price;
    private boolean available;
    private List<AvailabilityDTO> availabilityList;
    private List<Map<String, String>> images;
    private UserDTO host;
    private LocalDateTime createdAt;
    private boolean destacado;

    public PropertyCategory getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(PropertyCategory propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }



    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    // getter y setter
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getHost() {
        return host;
    }

    public void setHost(UserDTO host) {
        this.host = host;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public boolean isParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(boolean parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public Integer getEstrato() {
        return estrato;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public Boolean getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<AvailabilityDTO> getAvailabilityList() {
        return availabilityList;
    }

    public void setAvailabilityList(List<AvailabilityDTO> availabilityList) {
        this.availabilityList = availabilityList;
    }

    public List<Map<String, String>> getImages() {
        return images;
    }

    public void setImages(List<Map<String, String>> images) {
        this.images = images;
    }
}
