package com.busyatra.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "buses")
@Data
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String busName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int price;
    private int seatsAvailable;

    private String createdBy;    // SUPER_ADMIN or BUS_ADMIN
    private String companyName;  // Only for BUS_ADMIN

    // getters & setters
}
