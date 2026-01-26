package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PriceDTO {
    @DecimalMin(value = "0.001", message = "price must be greater than 0")
    private BigDecimal price;
}
