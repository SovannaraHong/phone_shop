package com.phone_shop.phoneshop.entity;

import com.phone_shop.phoneshop.config.security.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String placeOfBirth;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


}
