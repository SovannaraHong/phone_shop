package com.phone_shop.phoneshop.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.phone_shop.phoneshop.config.security.PermissionEnum.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RoleEnum {
    ADMIN(Set.of(BRAND_WRITE, BRAND_READ, MODEL_READ, MODEL_WRITE,
            REPORT)), SALE(Set.of(BRAND_READ, MODEL_READ)),
    STOCKER(Set.of(REPORT));

    Set<PermissionEnum> permissionEnums;

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
        Set<SimpleGrantedAuthority> grantedAuthorities = this.permissionEnums.stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.getDescription()))
                .collect(Collectors.toSet());
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + this.name());
        grantedAuthorities.add(role);
        System.out.println(grantedAuthorities);

        return grantedAuthorities;
    }


}
