package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.RegisterUserRequestDto;
import com.holovin.smidatestproject.model.User;

public class UserMapper {

    public static User ToUser(RegisterUserRequestDto registerUserRequestDto) {
        User user = new User();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setPassword(registerUserRequestDto.getPassword());
        user.setRoles(registerUserRequestDto.getRoles());
        return user;
    }
}
