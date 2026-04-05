package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.config.security.AuthUser;
import com.phone_shop.phoneshop.dto.UserDTO;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.entity.User;
import com.phone_shop.phoneshop.exception.ResourceBadRequestException;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.UserMapper;
import com.phone_shop.phoneshop.repository.RoleRepository;
import com.phone_shop.phoneshop.repository.UserRepository;
import com.phone_shop.phoneshop.service.RoleService;
import com.phone_shop.phoneshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public Optional<AuthUser> findByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

        AuthUser authUser = AuthUser.builder()
                .id(user.getId())
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

    public Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {

        Set<SimpleGrantedAuthority> grantedAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> roleAuthority = roles
                .stream()
                .flatMap(role -> getPermissionStream(role))
                .collect(Collectors.toSet());
        grantedAuthorities.addAll(roleAuthority);
        return grantedAuthorities;

    }

    public Stream<SimpleGrantedAuthority> getPermissionStream(Role roles) {
        return roles.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));

    }

    @Override
    public void delete(long id) {
        User userId = findById(id);
        userRepository.delete(userId);

    }

    @Override
    public User update(long id, UserDTO userDTO) {
        User userId = findById(id);
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ResourceBadRequestException("User", "username", userDTO.getUsername(), "User already exists");
        }
        userId.setFirstName(userDTO.getFirstName());
        userId.setLastName(userDTO.getLastName());
        userId.setUsername(userDTO.getUsername());
//        userId.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userId.setPhoneNumber(userDTO.getPhoneNumber());
        userId.setPlaceOfBirth(userDTO.getPlaceOfBirth());
        userId.setStatus(userDTO.getStatus());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            userId.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }


        Set<Role> role = userDTO.getRolesId().stream()
                .map(roleService::findById)
                .collect(Collectors.toSet());
        userId.setRoles(role);
        if (userDTO.getImagePath() != null) {
            userId.setImagePath(userDTO.getImagePath());
        }
        return userRepository.save(userId);

    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }


    @Override
    public User create(User user) {
        boolean exists = userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new ResourceBadRequestException("User", "username", user.getUsername(), "User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User createV1(UserDTO userDTO) {

        userDTO.getRolesId().stream().map(role ->
                roleRepository
                        .findById(role)
                        .orElseThrow(() -> new ResourceNotFoundException("Role", "id", role))
        );

        User user = userMapper.toUser(userDTO);
        boolean exists = userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new ResourceBadRequestException("User", "username", user.getUsername(), "User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        if (userDTO.getRolesId() != null || userDTO.getRolesId().isEmpty()) {
            userDTO.getRolesId().forEach(roleId -> {
                roles.add(roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId)));
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }


}
