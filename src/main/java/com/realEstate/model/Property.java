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

                    private Integer bedrooms;      // Habitaciones
                    private Integer bathrooms;     // Baños
                    private Double area;           // Área en m²
                    private String address;        // Dirección exacta
                    private Integer yearBuilt;     // Año de construcción
                    private String operationType;  // "RENT" o "SALE"
                    private Integer parkingSpaces; // Parqueaderos
                    private Integer estrato;       // Estrato socioeconómico
                    private Boolean petsAllowed;   // Acepta mascotas
                    private Boolean balcony;       // Tiene balcón
                    private Boolean terrace;       // Tiene terraza
                    private Boolean pool;          // Tiene piscina

                    @Enumerated(EnumType.STRING)
                    private PropertyType type;

                    private Double price;
                    private Boolean available;

                    @ManyToOne
                    @JoinColumn(name = "user_id")
                    private User host;

                    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
                    @JsonManagedReference
                    private List<Image> images;

                    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
                    @JsonManagedReference
                    private List<Availability> availabilityList = new ArrayList<>();

                    @CreationTimestamp
                    private LocalDateTime createdAt;

                    private boolean destacado = false; // NUEVO CAMPO
                    public boolean isDestacado() {
                        return destacado;
                    }

                    public void setDestacado(boolean destacado) {
                        this.destacado = destacado;
                    }
                    // Getter y Setter
                    public LocalDateTime getCreatedAt() {
                        return createdAt;
                    }
                    public void setCreatedAt(LocalDateTime createdAt) {
                        this.createdAt = createdAt;
                    }
                    // Getters y setters
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
                    public String getOperationType() { return operationType; }
                    public void setOperationType(String operationType) { this.operationType = operationType; }
                    public Integer getParkingSpaces() { return parkingSpaces; }
                    public void setParkingSpaces(Integer parkingSpaces) { this.parkingSpaces = parkingSpaces; }
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
                    public List<Image> getImages() { return images; }
                    public void setImages(List<Image> images) { this.images = images; }
                    public User getHost() { return host; }
                    public void setHost(User owner) { this.host = owner; }
                    public Boolean getAvailable() { return available; }
                    public void setAvailable(Boolean available) { this.available = available; }
                    public Double getPrice() { return price; }
                    public void setPrice(Double price) { this.price = price; }
                    public PropertyType getType() { return type; }
                    public void setType(PropertyType type) { this.type = type; }
                    public String getDescription() { return description; }
                    public void setDescription(String description) { this.description = description; }
                    public String getTitle() { return title; }
                    public void setTitle(String title) { this.title = title; }
                    public Long getId() { return id; }
                    public void setId(Long id) { this.id = id; }

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

                    public Property() {}

                    private Property(Builder builder) {
                        this.id = builder.id;
                        this.title = builder.title;
                        this.description = builder.description;
                        this.departamento = builder.departamento;
                        this.ciudad = builder.ciudad;
                        this.latitude = builder.latitude;
                        this.longitude = builder.longitude;
                        this.bedrooms = builder.bedrooms;
                        this.bathrooms = builder.bathrooms;
                        this.area = builder.area;
                        this.address = builder.address;
                        this.yearBuilt = builder.yearBuilt;
                        this.operationType = builder.operationType;
                        this.parkingSpaces = builder.parkingSpaces;
                        this.estrato = builder.estrato;
                        this.petsAllowed = builder.petsAllowed;
                        this.balcony = builder.balcony;
                        this.terrace = builder.terrace;
                        this.pool = builder.pool;
                        this.type = builder.type;
                        this.price = builder.price;
                        this.available = builder.available;
                        this.host = builder.owner;
                        this.images = builder.images;
                        this.availabilityList = new ArrayList<>();

                    }

                    public static class Builder {
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
                        private String operationType;
                        private Integer parkingSpaces;
                        private Integer estrato;
                        private Boolean petsAllowed;
                        private Boolean balcony;
                        private Boolean terrace;
                        private Boolean pool;
                        private PropertyType type;
                        private Double price;
                        private Boolean available;
                        private User owner;
                        private List<Image> images;
                        private List<Availability> availabilityList = new ArrayList<>();
                        public Builder id(Long id) { this.id = id; return this; }
                        public Builder title(String title) { this.title = title; return this; }
                        public Builder description(String description) { this.description = description; return this; }
                        public Builder departamento(String departamento) { this.departamento = departamento; return this; }
                        public Builder ciudad(String ciudad) { this.ciudad = ciudad; return this; }
                        public Builder latitude(Double latitude) { this.latitude = latitude; return this; }
                        public Builder longitude(Double longitude) { this.longitude = longitude; return this; }
                        public Builder bedrooms(Integer bedrooms) { this.bedrooms = bedrooms; return this; }
                        public Builder bathrooms(Integer bathrooms) { this.bathrooms = bathrooms; return this; }
                        public Builder area(Double area) { this.area = area; return this; }
                        public Builder address(String address) { this.address = address; return this; }
                        public Builder yearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; return this; }
                        public Builder operationType(String operationType) { this.operationType = operationType; return this; }
                        public Builder parkingSpaces(Integer parkingSpaces) { this.parkingSpaces = parkingSpaces; return this; }
                        public Builder estrato(Integer estrato) { this.estrato = estrato; return this; }
                        public Builder petsAllowed(Boolean petsAllowed) { this.petsAllowed = petsAllowed; return this; }
                        public Builder balcony(Boolean balcony) { this.balcony = balcony; return this; }
                        public Builder terrace(Boolean terrace) { this.terrace = terrace; return this; }
                        public Builder pool(Boolean pool) { this.pool = pool; return this; }
                        public Builder type(PropertyType type) { this.type = type; return this; }
                        public Builder price(Double price) { this.price = price; return this; }
                        public Builder available(Boolean available) { this.available = available; return this; }
                        public Builder owner(User owner) { this.owner = owner; return this; }
                        public Builder images(List<Image> images) { this.images = images; return this; }
                        public Builder availabilityList(List<Availability> availabilityList) {
                            this.availabilityList.clear();
                            if (availabilityList != null) {
                                for (Availability a : availabilityList) {
                                    a.setProperty(this.build());
                                    this.availabilityList.add(a);
                                }
                            }
                            return this;
                        }
                        public Property build() {
                            return new Property(this);
                        }
                    }

                    public static Builder builder() {
                        return new Builder();
                    }
                }