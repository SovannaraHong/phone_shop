package com.phone_shop.phoneshop.mapper;

import com.phone_shop.phoneshop.dto.UserDTO;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "placeOfBirth", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserDTO userDTO);

    @Mapping(target = "rolesId", source = "roles")
    UserDTO toUserDTO(User user);

    default Set<Long> mapRolesToIds(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }
}
