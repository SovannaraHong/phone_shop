package com.phone_shop.phoneshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_import")
public class ProductHistoryImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id")
    private Long id;
    @Column(name = "import_unit")

    private Integer importUnit;
    @Column(name = "import_Date")

    private LocalDateTime importDate;
    @Column(name = "import_price_per_unit")

    private BigDecimal pricePerUnit;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
