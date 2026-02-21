package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "username is required")
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 6)
    private String password;

    @Pattern(regexp = "^[0-9]{8,15}$", message = "Phone number must be valid")
    private String phoneNumber;

    @NotBlank(message = "role is required")
    private Set<Long> rolesId;

    private String placeOfBirth;
    private String imagePath;

}
