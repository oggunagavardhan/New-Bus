package com.busyatra.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bus_seats")
public class BusSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer busId;

    private String seatCode;     // L1, U2
    private String deck;         // LOWER / UPPER
    private String seatType;     // sleeper / seater
    private Integer price;
    private String gender;       // male / female
    private boolean booked;
}
