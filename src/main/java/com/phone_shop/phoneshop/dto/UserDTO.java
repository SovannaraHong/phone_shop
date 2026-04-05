package com.phone_shop.phoneshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 50, message = "first name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 2, max = 50, message = "last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "username is required")
    @Size(min = 2, max = 50, message = "username must be between 2 and 50 characters")
    private String username;

    //    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @Pattern(regexp = "^[0-9]{8,15}$", message = "Phone number must be 8-15 digits")
    private String phoneNumber;

    @NotEmpty(message = "role is required")
    private Set<Long> rolesId;

    private String placeOfBirth;
    private String imagePath;
    private String status;

}