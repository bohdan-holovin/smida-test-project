package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.auth.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.Role;
import com.holovin.smidatestproject.model.User;

import java.util.stream.Collectors;

public class UserDtoMapper {

    public static User toUser(UserRegisterRequestDto userRegisterRequestDto) {
        User user = new User();
        user.setUsername(userRegisterRequestDto.getUsername());
        user.setPassword(userRegisterRequestDto.getPassword());
        user.setRoles(userRegisterRequestDto.getRoles().stream()
                .map(it -> Role.valueOf(it.name()))
                .collect(Collectors.toSet()));
        return user;
    }
}
