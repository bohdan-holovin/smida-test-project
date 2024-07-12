package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.user.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UserUpdateRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.model.User;

public class UserDtoMapper {

    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public static User toUser(UserUpdateRequestDto userUpdateRequestDto) {
        User user = new User();
        user.setId(userUpdateRequestDto.getId());
        user.setUsername(userUpdateRequestDto.getUsername());
        user.setPassword(userUpdateRequestDto.getPassword());
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setLastName(userUpdateRequestDto.getLastName());
        user.setEmail(userUpdateRequestDto.getEmail());
        user.setPhone(userUpdateRequestDto.getPhone());
        user.setAddress(userUpdateRequestDto.getAddress());
        return user;
    }

    public static User toUser(UserRegisterRequestDto userRegisterRequestDto) {
        User user = new User();
        user.setUsername(userRegisterRequestDto.getUsername());
        user.setPassword(userRegisterRequestDto.getPassword());
        user.setFirstName(userRegisterRequestDto.getFirstName());
        user.setLastName(userRegisterRequestDto.getLastName());
        user.setEmail(userRegisterRequestDto.getEmail());
        user.setPhone(userRegisterRequestDto.getPhone());
        user.setAddress(userRegisterRequestDto.getAddress());
        return user;
    }
}
