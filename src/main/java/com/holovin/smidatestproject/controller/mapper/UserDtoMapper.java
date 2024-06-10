package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.User;

public class UserDtoMapper {

    public static User toUser(UserRegisterRequestDto userRegisterRequestDto) {
        User user = new User();
        user.setUsername(userRegisterRequestDto.getUsername());
        user.setPassword(userRegisterRequestDto.getPassword());
        user.setRoles(userRegisterRequestDto.getRoles());
        return user;
    }
}
