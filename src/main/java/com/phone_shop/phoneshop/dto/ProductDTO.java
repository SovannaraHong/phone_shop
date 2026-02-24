package com.phone_shop.phoneshop.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    @NotNull(message = "model id is required")
    private Long modelId;
    @NotNull(message = "color id is required")
    private Long colorId;
}
