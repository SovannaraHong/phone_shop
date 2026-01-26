package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SaleDTO {
    @NotEmpty
    private List<ProductSoldDTO> product;
    private LocalDate soldDate;
}
