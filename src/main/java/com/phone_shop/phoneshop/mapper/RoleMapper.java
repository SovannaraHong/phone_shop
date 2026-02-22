package com.phone_shop.phoneshop.mapper;

import com.phone_shop.phoneshop.dto.RoleDTO;
import com.phone_shop.phoneshop.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "id", ignore = true)
    Role toRole(RoleDTO roleDTO);

    // Entity → Response
//    @Mapping(target = "permissions", expression = "java(mapPermissionNames(role.getPermissions()))")
    @Mapping(target = "permissions", ignore = true)
    RoleDTO toRoleDTO(Role role);

//    default Set<String> mapPermissionNames(Set<Permission> permissions) {
//        if (permissions == null) return new HashSet<>();
//
//        return permissions.stream()
//                .map(Permission::getName)
//                .collect(Collectors.toSet());
//    }
}
