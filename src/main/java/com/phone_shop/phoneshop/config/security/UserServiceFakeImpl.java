package com.phone_shop.phoneshop.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceFakeImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AuthUser> findByUsername(String username) {

        List<AuthUser> authUsers = List.of(

                new AuthUser("nara", passwordEncoder.encode("nara123"), Role.ADMIN.getSimpleGrantedAuthority(),
                        true, true, true, true),
                new AuthUser("thida", passwordEncoder.encode("thida123"), Role.SALE.getSimpleGrantedAuthority(),
                        true, true, true, true),
                new AuthUser("cheata", passwordEncoder.encode("cheata123"), Role.ADMIN.getSimpleGrantedAuthority(),
                        true, true, true, true)
        );

        return authUsers.stream().filter(name -> name.getUsername()
                        .equals(username))
                .findFirst();
    }
}
