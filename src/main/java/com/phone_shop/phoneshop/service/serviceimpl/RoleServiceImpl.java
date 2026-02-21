package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.dto.RoleDTO;
import com.phone_shop.phoneshop.entity.Permission;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.RoleMapper;
import com.phone_shop.phoneshop.repository.PermissionRepository;
import com.phone_shop.phoneshop.repository.RoleRepository;
import com.phone_shop.phoneshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();

    }

    @Override
    public Role create(RoleDTO roledto) {

        if (Objects.isNull(roledto.getName()) || roledto.getName().isEmpty()) {
            throw new ResourceBadRequestException("Role", "name", roledto.getName(), "is invalid");
        }

        if (roledto.getPermissions() == null || roledto.getPermissions().isEmpty()) {
            throw new ResourceBadRequestException("Role", "permissions", roledto.getPermissions(), "is invalid");
        }
        if (roleRepository.existsByName(roledto.getName())) {
            throw new ResourceBadRequestException("Role", "name", roledto.getName(), " name already exists");
        }
        Role role = roleMapper.toRole(roledto);

        Set<Permission> permissions = roledto.getPermissions()
                .stream()
                .map(permissionName ->
                        permissionRepository
                                .findByName(permissionName)
                                .orElseThrow(() ->
                                        new ResourceNotFoundException("Permission", "name", permissionName)))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);

        return roleRepository.save(role);

    }


    @Override
    public Role update(Long id, RoleDTO roleDTO) {
        Role byId = findById(id);

        if (roleDTO.getPermissions() != null || !roleDTO.getPermissions().isEmpty()) {
            Set<Permission> permissions = roleDTO.getPermissions().stream().map(permissionName ->
                            permissionRepository
                                    .findByName(permissionName)
                                    .orElseThrow(() -> new ResourceNotFoundException("Permission", "name", permissionName)))
                    .collect(Collectors.toSet());
            byId.setPermissions(permissions);
        }
        return roleRepository.save(byId);
    }

    @Override
    public void delete(Long id) {
        Role roleId = findById(id);
        roleRepository.delete(roleId);

    }
}
