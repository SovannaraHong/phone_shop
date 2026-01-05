package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @Column(name = "product_price")

    private Double price;
    @Column(name = "product_quantity")

    private Integer quantity;
    @Column(name = "product_description")
    private String description;
    @Column(name = "product_createAt")

    private LocalDate createdAt;
}
