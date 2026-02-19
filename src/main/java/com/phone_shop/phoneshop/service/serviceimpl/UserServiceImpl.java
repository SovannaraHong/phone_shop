package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.config.security.AuthUser;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.entity.User;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.repository.UserRepository;
import com.phone_shop.phoneshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        AuthUser authUser = AuthUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())

                .build();
        return Optional.ofNullable(authUser);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User create(User user) {

        boolean exists = userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new ResourceBadRequestException("User", "username", user.getUsername());
        }
        return userRepository.save(user);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {

        Set<SimpleGrantedAuthority> grantedAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> roleAuthority = roles.stream().flatMap(role -> getPermissionStream(role)).collect(Collectors.toSet());
        grantedAuthorities.addAll(roleAuthority);
        return grantedAuthorities;

    }

    public Stream<SimpleGrantedAuthority> getPermissionStream(Role roles) {
        return roles.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));

    }

}
