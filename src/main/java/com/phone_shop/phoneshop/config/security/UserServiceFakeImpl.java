package com.phone_shop.phoneshop.config.security;

import com.phone_shop.phoneshop.entity.User;
import com.phone_shop.phoneshop.service.UserService;
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

                new AuthUser("nara", passwordEncoder.encode("nara123"), RoleEnum.ADMIN.getSimpleGrantedAuthority(),
                        true, true, true, true),
                new AuthUser("thida", passwordEncoder.encode("thida123"), RoleEnum.SALE.getSimpleGrantedAuthority(),
                        true, true, true, true),
                new AuthUser("cheata", passwordEncoder.encode("cheata123"), RoleEnum.ADMIN.getSimpleGrantedAuthority(),
                        true, true, true, true)
        );

        return authUsers.stream().filter(name -> name.getUsername()
                        .equals(username))
                .findFirst();
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User update(long id, User user) {
        return null;
    }
}
