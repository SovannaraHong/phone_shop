package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.config.security.AuthUser;
import com.phone_shop.phoneshop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findByUsername(String username);

    User findById(long id);

    List<User> getUsers();

    User findByName(String username);

    User create(User user);

    void delete(long id);

    User update(long id, User user);

}
