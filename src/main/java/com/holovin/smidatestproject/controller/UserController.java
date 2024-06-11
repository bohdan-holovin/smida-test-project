package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.controller.dto.request.UserAuthRequestDto;
import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.exceptions.UserIsUnauthorizedException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserDetailsServiceImpl;
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

@RestController
@AllArgsConstructor
@RequestMapping()
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/auth/not-secured")
    public ResponseEntity<String> notSecured() {
        logger.info("Accessing non-secured endpoint");
        return ResponseEntity.ok("This endpoint is not secure");
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        logger.info("Registering user with username request: {}", userRegisterRequestDto.getUsername());
        User user = userDetailsServiceImpl.registerUser(toUser(userRegisterRequestDto));
        logger.info("User registered successfully with username: {}", user.getUsername());
        return ResponseEntity.ok("User register successfully with username: " + user.getUsername());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        logger.info("Attempting to authenticate user with username: {}", userAuthRequestDto.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthRequestDto.getUsername(),
                        userAuthRequestDto.getPassword())
                );
        if (!authentication.isAuthenticated()) {
            logger.info("Authentication failed for user: {}", userAuthRequestDto.getUsername());
            throw new UserIsUnauthorizedException(userAuthRequestDto.getUsername());
        }
        String generatedToken = jwtService.generateToken(userAuthRequestDto.getUsername());
        logger.info("Authentication successful for user: {}", userAuthRequestDto.getUsername());
        return ResponseEntity.ok(generatedToken);
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> userProfile() {
        logger.info("Accessing user profile endpoint");
        return ResponseEntity.ok("You are logged in USER");
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> adminProfile() {
        logger.info("Accessing admin profile endpoint");
        return ResponseEntity.ok("You are logged in ADMIN");
    }
}
