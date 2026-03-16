package com.phone_shop.phoneshop.controller;

import com.phone_shop.phoneshop.dto.UserDTO;
import com.phone_shop.phoneshop.entity.User;
import com.phone_shop.phoneshop.mapper.UserMapper;
import com.phone_shop.phoneshop.payload.response.UserResponse;
import com.phone_shop.phoneshop.repository.UserRepository;
import com.phone_shop.phoneshop.service.S3Service;
import com.phone_shop.phoneshop.service.UserService;
import com.phone_shop.phoneshop.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    //    private final RoleService roleService;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;
    private final S3Service s3Service;
    private final UserRepository userRepository;


    //    @PostMapping("/register")
//    public ResponseEntity<?> signIn(@RequestBody UserDTO userDTO) {
//
//        userDTO.getRolesId()
//                .stream()
//                .map(roleId -> roleRepository.findById(roleId)
//                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", roleId)));
//        User user = userMapper.toUser(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setCredentialsNonExpired(true);
//        user.setEnabled(true);
//
//
//        Set<Role> roles = new HashSet<>();
//        if (userDTO.getRolesId() != null) {
//            userDTO.getRolesId().forEach(id -> {
//
//                roles.add(roleService.findById(id));
//            });
//        }
//        user.setRoles(roles);
//
//
//        User userResponse = userService.create(user);
//
//        UserDTO userDto = userMapper.toUserDTO(userResponse);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
//
//    }
    @PostMapping("/register")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.createV1(userDTO);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUsername())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(HttpStatus.CREATED, "User created Successfully", userResponse));
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
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {

        User user = userService.update(id, userDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userMapper.toUserDTO(user));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByName(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseUtil.deleteSuccess("User", id));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile file) throws Exception {

        User user = userService.findById(id);

        // Upload image to S3
        String url = s3Service.uploadFile(file, "user_images");

        // Only update the image field
        user.setImagePath(url);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}
