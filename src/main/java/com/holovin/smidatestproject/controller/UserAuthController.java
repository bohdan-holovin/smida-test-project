package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.controller.dto.user.request.UserAuthRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.exception.UserIsUnauthorizedException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.holovin.smidatestproject.controller.mapper.UserDtoMapper.toUser;

@RestController
@AllArgsConstructor
@RequestMapping
public class UserAuthController {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    private final UserAuthService userAuthService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/not-secured")
    public ResponseEntity<String> notSecured() {
        logger.info("Accessing non-secured endpoint");
        return ResponseEntity.ok("This endpoint is not secure");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        logger.info("Registering user with username request: {}", userRegisterRequestDto.getUsername());
        User user = userAuthService.registerUser(toUser(userRegisterRequestDto));
        logger.info("User registered successfully with username: {}", user.getUsername());
        return ResponseEntity.ok("User register successfully with username: " + user.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@Valid @RequestBody UserAuthRequestDto userAuthRequestDto) {
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
}
