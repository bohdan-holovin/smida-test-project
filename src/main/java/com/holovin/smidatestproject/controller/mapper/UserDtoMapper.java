package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.user.request.RegisterUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPasswordRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPersonalDataRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.model.User;

public class UserDtoMapper {

    public static User toUser(RegisterUserRequestDto registerUserRequestDto) {
        User user = new User();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setPassword(registerUserRequestDto.getPassword());
        user.setFirstName(registerUserRequestDto.getFirstName());
        user.setLastName(registerUserRequestDto.getLastName());
        user.setEmail(registerUserRequestDto.getEmail());
        user.setPhone(registerUserRequestDto.getPhone());
        user.setAddress(registerUserRequestDto.getAddress());
        user.setDateOfBirth(registerUserRequestDto.getDateOfBirth());
        return user;
    }

    public static User toUser(UpdateUserPersonalDataRequestDto updateUserPersonalDataRequestDto) {
        User user = new User();
        user.setUsername(updateUserPersonalDataRequestDto.getUsername());
        user.setFirstName(updateUserPersonalDataRequestDto.getFirstName());
        user.setLastName(updateUserPersonalDataRequestDto.getLastName());
        user.setEmail(updateUserPersonalDataRequestDto.getEmail());
        user.setPhone(updateUserPersonalDataRequestDto.getPhone());
        user.setAddress(updateUserPersonalDataRequestDto.getAddress());
        user.setDateOfBirth(updateUserPersonalDataRequestDto.getDateOfBirth());
        return user;
    }

    public static User toUser(UpdateUserPasswordRequestDto userPasswordRequestDto) {
        User user = new User();
        user.setUsername(userPasswordRequestDto.getUsername());
        user.setPassword(userPasswordRequestDto.getPassword());
        return user;
    }

    public static UserResponseDto toUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setCompanyId(user.getCompany().getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setDateOfStartWork(user.getDateOfStartWork());
        dto.setPosition(user.getPosition());
        return dto;
    }
}
