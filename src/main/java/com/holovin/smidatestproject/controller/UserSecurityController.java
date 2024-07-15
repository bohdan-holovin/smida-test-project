package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.service.JwtService;
import com.holovin.smidatestproject.controller.dto.user.request.AuthUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.RegisterUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPasswordRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.exception.UserIsUnauthorizedException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserSecurityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.holovin.smidatestproject.controller.mapper.UserDtoMapper.toUser;
import static com.holovin.smidatestproject.controller.mapper.UserDtoMapper.toUserResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserSecurityController {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityController.class);

    private final UserSecurityService userSecurityService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/not-secured")
    public ResponseEntity<String> notSecured() {
        logger.info("Accessing non-secured endpoint");
        return ResponseEntity.ok("This endpoint is not secure");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto) {
        logger.info("Registering user with username request: {}", registerUserRequestDto.getUsername());
        User user = userSecurityService.registerUser(toUser(registerUserRequestDto));
        logger.info("User registered successfully with username: {}", user.getUsername());
        return ResponseEntity.ok("User register successfully with username: " + user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@Valid @RequestBody AuthUserRequestDto authUserRequestDto) {
        logger.info("Attempting to authenticate user with username: {}", authUserRequestDto.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        authUserRequestDto.getUsername(),
                        authUserRequestDto.getPassword())
                );
        if (!authentication.isAuthenticated()) {
            logger.info("Authentication failed for user: {}", authUserRequestDto.getUsername());
            throw new UserIsUnauthorizedException(authUserRequestDto.getUsername());
        }
        String generatedToken = jwtService.generateToken(authUserRequestDto.getUsername());
        logger.info("Authentication successful for user: {}", authUserRequestDto.getUsername());
        return ResponseEntity.ok(generatedToken);
    }

    @PutMapping("/users/")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<UserResponseDto> updateUserPassword(
            @Valid @RequestBody UpdateUserPasswordRequestDto userPasswordRequestDto
    ) {
        logger.info("Updating user password request: {}", userPasswordRequestDto);
        UserResponseDto updatedUser = toUserResponseDto(userSecurityService.updateUserPassword(toUser(userPasswordRequestDto)));
        logger.info("Updated user password response: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
}
