package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brand extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "brand_id")
    private Long id;


    @Column(name = "brand_name")
    private String name;

    @Column(name = "brand_country")
    private String country;

//    @CreatedDate
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
}