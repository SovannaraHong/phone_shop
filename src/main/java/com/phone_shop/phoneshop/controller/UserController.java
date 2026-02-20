package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.UserDTO;
import com.phone_shop.phoneshop.entity.Role;
import com.phone_shop.phoneshop.entity.User;
import com.phone_shop.phoneshop.exception.ResourceNotFoundException;
import com.phone_shop.phoneshop.mapper.UserMapper;
import com.phone_shop.phoneshop.repository.RoleRepository;
import com.phone_shop.phoneshop.service.RoleService;
import com.phone_shop.phoneshop.service.UserService;
import com.phone_shop.phoneshop.service.util.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO) {

        userDTO.getRolesId()
                .stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", roleId)));
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);


        Set<Role> roles = new HashSet<>();
        if (userDTO.getRolesId() != null) {
            userDTO.getRolesId().forEach(id -> {

                roles.add(roleService.findById(id));
            });
        }
        user.setRoles(roles);
        User userResponse = userService.create(user);

        UserDTO userDto = userMapper.toUserDTO(userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);

    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.findById(id);
        Set<Role> roles = new HashSet<>();
        if (userDTO.getRolesId() != null && !userDTO.getRolesId().isEmpty()) {
            userDTO.getRolesId().forEach(roleId -> {
                Role role = roleService.findById(roleId);
                roles.add(role);
            });

        }
        user.setRoles(roles);
        User update = userService.update(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toUserDTO(update));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByName(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseHelper.deleteSuccess("User", id));
    }
}
