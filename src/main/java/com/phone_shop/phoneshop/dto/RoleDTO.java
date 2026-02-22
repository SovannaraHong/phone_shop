package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO {
    @NotBlank(message = "role name is required")
    private String name;
    @NotEmpty(message = "permissions must be required")
    private Set<String> permissions;

}
