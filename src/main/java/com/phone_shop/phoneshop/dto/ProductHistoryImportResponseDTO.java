package com.phone_shop.phoneshop.dto;

import com.phone_shop.phoneshop.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductHistoryImportResponseDTO {
    private Long id;
    private Integer importUnit;
    private BigDecimal pricePerUnit;
    private LocalDateTime importDate;

    private Product product;


}