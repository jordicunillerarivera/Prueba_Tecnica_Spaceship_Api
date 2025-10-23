package org.jordicunillerarivera.spaceship.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "spaceship")
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String model;
    private String manufacturer;
    private Integer crewCapacity;

    private LocalDateTime createAt = LocalDateTime.now();

    // Constructor
    public Spaceship() {
        this.createAt = LocalDateTime.now();
    }

    public Spaceship(Long id, String name, String model, String manufacturer, Integer crewCapacity, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.crewCapacity = crewCapacity;
        this.createAt = createAt != null ? createAt : LocalDateTime.now();
    }

    // Setters & Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getCrewCapacity() {
        return crewCapacity;
    }

    public void setCrewCapacity(Integer crewCapacity) {
        this.crewCapacity = crewCapacity;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
