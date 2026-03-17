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
    private String modelName;
    private String colorName;
    private String description;
    private Long brandId;
}