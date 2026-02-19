package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Role roleId = roleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(roleId);

    }

}
