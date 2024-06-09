package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.controller.dto.AuthRequest;
import com.holovin.smidatestproject.model.UserInfo;
import com.holovin.smidatestproject.service.JwtService;
import com.holovin.smidatestproject.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping()
public class UserController {

    private UserInfoService service;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/not_secured")
    public String notSecured() {
        return "This endpoint is not secure";
    }

    @PostMapping("/auth/register")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/auth/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/user/profile")
    @PreAuthorize("hasAuthority('USER')")
    public String userProfile() {
        return "You are logged in USER";
    }

    @GetMapping("/admin/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminProfile() {
        return "You are logged in ADMIN";
    }
}
