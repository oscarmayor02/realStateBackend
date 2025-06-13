package com.realEstate.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
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

    // Constructor requerido por JPA
    public Property() {}

    // Constructor privado para el builder
    private Property(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.location = builder.location;
        this.type = builder.type;
        this.price = builder.price;
        this.available = builder.available;
        this.owner = builder.owner;
        this.images = builder.images;
    }

    // Métodos getter y setter...

    // Builder manual
    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String location;
        private PropertyType type;
        private Double price;
        private Boolean available;
        private User owner;
        private List<Image> images;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder location(String location) { this.location = location; return this; }
        public Builder type(PropertyType type) { this.type = type; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder available(Boolean available) { this.available = available; return this; }
        public Builder owner(User owner) { this.owner = owner; return this; }
        public Builder images(List<Image> images) { this.images = images; return this; }

        public Property build() {
            return new Property(this);
        }
    }

    // Método para obtener el builder
    public static Builder builder() {
        return new Builder();
    }

}
