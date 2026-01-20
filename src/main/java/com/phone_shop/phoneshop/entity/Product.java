package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products"
        , uniqueConstraints = {@UniqueConstraint(columnNames = {"model_id", "color_id"})})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;


    @Column(name = "product_name", unique = true)
    private String name;


    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;


    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "sale_per_product")
    private BigDecimal salePrice;

    @Column(name = "Stock_product")
    private Integer unit;

}
