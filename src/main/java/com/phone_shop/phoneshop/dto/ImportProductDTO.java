package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ImportProductDTO {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Import Unit is required")
    @Min(value = 1, message = "Import Unit must be greater than 0")
    private Integer importUnit;

    @NotNull(message = "Price per unit is required")
    @DecimalMin(value = "0.0001", message = "Price must be greater than 0.0001")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Import Date cannot be null")
    private LocalDateTime importDate;
}

