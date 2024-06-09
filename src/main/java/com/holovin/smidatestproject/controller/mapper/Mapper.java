package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.RegisterUserRequestDto;
import com.holovin.smidatestproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public static User mapToUser(RegisterUserRequestDto registerUserRequestDto) {
        User user = new User();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setPassword(registerUserRequestDto.getPassword());
        user.setRoles(registerUserRequestDto.getRoles());
        return user;
    }
}
