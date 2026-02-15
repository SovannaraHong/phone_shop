package com.phone_shop.phoneshop.config.security;

import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findByUsername(String username);
}
