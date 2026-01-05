package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;


    @Column(name = "brand_name")
    private String name;

    @Column(name = "from_country")
    private String country;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}