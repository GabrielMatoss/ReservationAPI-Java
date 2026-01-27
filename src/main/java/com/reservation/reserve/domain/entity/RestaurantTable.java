package com.reservation.reserve.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tableNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String location;

    public RestaurantTable() {
    }

    public RestaurantTable(Long id, String tableNumber, Integer capacity, String location) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
