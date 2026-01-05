package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;

    // Ensure there are no typos here
    @Column(name = "brand_name", length = 255)
    private String name;

    @Column(name = "from_country", length = 255)
    private String country;

    @Column(name = "created_at")
    private LocalDate createdAt;
}