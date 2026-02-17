package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.config.security.AuthUser;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findByUsername(String username);
}
