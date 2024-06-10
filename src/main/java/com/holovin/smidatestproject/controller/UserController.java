package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.controller.dto.request.UserAuthRequestDto;
import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.exceptions.UserIsUnauthorizedException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.holovin.smidatestproject.controller.mapper.UserMapper.toUser;

@RestController
@AllArgsConstructor
@RequestMapping()
public class UserController {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/not-secured")
    public ResponseEntity<String> notSecured() {
        return ResponseEntity.ok("This endpoint is not secure");
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        User user = userDetailsServiceImpl.registerUser(toUser(userRegisterRequestDto));
        return ResponseEntity.ok("User register successfully with username: " + user.getUsername());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthRequestDto.getUsername(),
                        userAuthRequestDto.getPassword())
                );
        if (!authentication.isAuthenticated()) {
            throw new UserIsUnauthorizedException(userAuthRequestDto.getUsername());
        }
        return ResponseEntity.ok(jwtService.generateToken(userAuthRequestDto.getUsername()));
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("You are logged in USER");
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> adminProfile() {
        return ResponseEntity.ok("You are logged in ADMIN");
    }
}
