package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.user.request.AuthUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.RegisterUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPasswordRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPersonalDataRequestDto;
import com.holovin.smidatestproject.model.User;

public class JsonUserMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonRegisterUserRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new RegisterUserRequestDto(
                        user.getUsername(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        user.getDateOfBirth()
                )
        );
    }

    public static String toJsonAuthUserRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(new AuthUserRequestDto(user.getUsername(), user.getPassword()));
    }

    public static String toJsonUpdateUserPersonalDataRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new UpdateUserPersonalDataRequestDto(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        user.getDateOfBirth()
                )
        );
    }

    public static String toJsonUpdateUserPasswordRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new UpdateUserPasswordRequestDto(
                        user.getUsername(),
                        user.getPassword()
                )
        );
    }
}
