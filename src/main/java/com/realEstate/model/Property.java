package com.realEstate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean parkingSpaces;
    private Integer estrato;
    private Boolean petsAllowed;
    private Boolean balcony;
    private Boolean terrace;
    private Boolean pool;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Enumerated(EnumType.STRING)
    private PropertyCategory propertyTypeName;

    private Double price;
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Availability> availabilityList = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean destacado = false;

    // ---------- GETTERS Y SETTERS ----------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }

    public Boolean getParkingSpaces() { return parkingSpaces; }
    public void setParkingSpaces(boolean parkingSpaces) { this.parkingSpaces = parkingSpaces; }

    public Integer getEstrato() { return estrato; }
    public void setEstrato(Integer estrato) { this.estrato = estrato; }

    public Boolean getPetsAllowed() { return petsAllowed; }
    public void setPetsAllowed(Boolean petsAllowed) { this.petsAllowed = petsAllowed; }

    public Boolean getBalcony() { return balcony; }
    public void setBalcony(Boolean balcony) { this.balcony = balcony; }

    public Boolean getTerrace() { return terrace; }
    public void setTerrace(Boolean terrace) { this.terrace = terrace; }

    public Boolean getPool() { return pool; }
    public void setPool(Boolean pool) { this.pool = pool; }

    public OperationType getOperationType() { return operationType; }
    public void setOperationType(OperationType operationType) { this.operationType = operationType; }

    public PropertyCategory getPropertyTypeName() { return propertyTypeName; }
    public void setPropertyTypeName(PropertyCategory propertyTypeName) { this.propertyTypeName = propertyTypeName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public User getHost() { return host; }
    public void setHost(User host) { this.host = host; }

    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }

    public List<Availability> getAvailabilityList() { return availabilityList; }
    public void setAvailabilityList(List<Availability> availabilityList) {
        this.availabilityList.clear();
        if (availabilityList != null) {
            for (Availability a : availabilityList) {
                a.setProperty(this);
                this.availabilityList.add(a);
            }
        }
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public boolean isDestacado() { return destacado; }
    public void setDestacado(boolean destacado) { this.destacado = destacado; }
}
