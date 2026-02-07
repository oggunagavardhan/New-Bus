package com.busyatra.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer busId;       // ⭐ REQUIRED

    private String email;
    private String busName;
    private String source;
    private String destination;

    private String seats;
    private int amount;

    private String upiId;
    private String status;
}
