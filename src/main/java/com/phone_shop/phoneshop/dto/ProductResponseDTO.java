package com.phone_shop.phoneshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String imagePath;
    private Integer unit;
    private BigDecimal salePrice;

    private String typeSell;

    private String modelName;
    private String colorName;
    private String description;
    private Boolean active;
    private Long brandId;
}