package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long id;
    @Column(name = "model_name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
