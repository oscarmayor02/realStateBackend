package com.realEstate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    // Unique ID for the image
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // URL or path to the image
    private String url;

    // Property the image belongs to
    @ManyToOne
    @JsonBackReference
    private Property property;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
