package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelDTO {
    @NotBlank(message = "Model name must be required")
    private String name;
    @NotNull(message = "Brand id must be required")
    private Long brandId;

}
