package com.phone_shop.phoneshop.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private Set<Long> rolesId;

}
