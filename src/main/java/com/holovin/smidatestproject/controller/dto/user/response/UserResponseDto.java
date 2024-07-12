package com.holovin.smidatestproject.controller.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
