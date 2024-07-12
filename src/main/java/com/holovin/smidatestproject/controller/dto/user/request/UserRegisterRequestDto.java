package com.holovin.smidatestproject.controller.dto.user.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone cannot be null")
    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "\\+?[0-9. ()-]{7,25}", message = "Phone number is not valid")
    private String phone;

    @Size(max = 100, message = "Address must be less than 100 characters")
    private String address;

    @NotNull(message = "Roles cannot be null")
    @NotEmpty(message = "Roles cannot be empty")
    private Set<Role> roles;

    public enum Role {
        USER,
        ADMIN
    }
}
