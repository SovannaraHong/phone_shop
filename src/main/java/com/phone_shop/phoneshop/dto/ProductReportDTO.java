package com.phone_shop.phoneshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductReportDTO {

    private Long productId;
    private String productName;
    private Integer productUnit;
    private BigDecimal totalAmount;
}
