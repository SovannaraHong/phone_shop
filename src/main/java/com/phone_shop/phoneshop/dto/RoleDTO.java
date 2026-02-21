package com.phone_shop.phoneshop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleDTO {
    private String name;
    private Set<String> permissions;

}
