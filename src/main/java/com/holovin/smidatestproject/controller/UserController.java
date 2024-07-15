package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPersonalDataRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.controller.mapper.UserDtoMapper;
import com.holovin.smidatestproject.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.holovin.smidatestproject.controller.mapper.UserDtoMapper.toUser;
import static com.holovin.smidatestproject.controller.mapper.UserDtoMapper.toUserResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.info("Fetching all users");
        List<UserResponseDto> users = userService.getAllUsers().stream()
                .map(UserDtoMapper::toUserResponseDto)
                .collect(Collectors.toList());
        logger.info("Found {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        logger.info("Fetching user with id {}", id);
        UserResponseDto user = toUserResponseDto(userService.getUserById(id));
        logger.info("Found user response: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'COMPANY_OWNER', 'ACCOUNTANT','ADMIN')")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user with username {}", username);
        UserResponseDto user = toUserResponseDto(userService.getUserByUsername(username));
        logger.info("Found user response: {}", user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<UserResponseDto> updateUserPersonalData(
            @Valid @RequestBody UpdateUserPersonalDataRequestDto updateUserPersonalDataRequestDto
    ) {
        logger.info("Updating user personal data request: {}", updateUserPersonalDataRequestDto);
        UserResponseDto updatedUser = toUserResponseDto(userService.updateUserPersonalData(toUser(updateUserPersonalDataRequestDto)));
        logger.info("Updated user personal data response: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        logger.info("Deleting user with username {}", username);
        userService.deleteUserByUsername(username);
        logger.info("Deleted user with username {}", username);
        return ResponseEntity.ok().build();
    }
}
