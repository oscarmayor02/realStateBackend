package com.realEstate.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "properties")

public class Property {

    // Unique identifier for the property
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Short title of the property
    private String title;

    // Detailed description
    private String description;

    // Location or address
    private String location;

    // Property type: RENT or SALE
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    // Price in local currency
    private Double price;

    // Indicates if the property is available
    private Boolean available;

    // Property owner
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // Associated images
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
