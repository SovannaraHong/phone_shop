package com.phone_shop.phoneshop.service;

import com.phone_shop.phoneshop.dto.RoleDTO;
import com.phone_shop.phoneshop.entity.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);

    Role findByName(String name);

    List<Role> getRoles();

    Role create(RoleDTO roledto);

    Role update(Long id, RoleDTO roleDTO);

    void delete(Long id);
}
