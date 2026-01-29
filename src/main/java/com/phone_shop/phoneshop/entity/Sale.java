package com.phone_shop.phoneshop.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;
    @Column(name = "active_Sale")
    private Boolean IsActive;
    @Column(name = "sold_date")
    private LocalDateTime soldDate;

}
