package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.RoleDTO;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.mapper.RoleMapper;
import com.phone_shop.phoneshop.service.RoleService;
import com.phone_shop.phoneshop.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @PreAuthorize("hasAnyAuthority('role:read')")
    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Role roleId = roleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(roleId);

    }

    @PreAuthorize("hasAnyAuthority('role:read')")

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        Role role = roleService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @PreAuthorize("hasAnyAuthority('role:read')")

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.status(HttpStatus.OK).body(roles);

    }

    @PreAuthorize("hasAnyAuthority('role:write')")

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoleDTO roleDTO) {

        Role roles = roleService.create(roleDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(roles);
//        return ResponseEntity.status(HttpStatus.CREATED).body(roleMapper.toRoleDTO(roles));

    }

    @PreAuthorize("hasAnyAuthority('role:write')")

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Role update = roleService.update(id, roleDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(update);

    }

    @PreAuthorize("hasAnyAuthority('role:write')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok(ResponseUtil.deleteSuccess("Role", id));
    }

}
