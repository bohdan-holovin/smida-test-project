package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.auth.request.UserAuthRequestDto;
import com.holovin.smidatestproject.controller.dto.auth.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.User;

import java.util.stream.Collectors;

public class JsonUserMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonUserRegisterRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new UserRegisterRequestDto(user.getUsername(), user.getPassword(), user.getRoles().stream()
                        .map(it -> UserRegisterRequestDto.Role.valueOf(it.name()))
                        .collect(Collectors.toSet()))
        );
    }

    public static String toJsonUserAuthRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(new UserAuthRequestDto(user.getUsername(), user.getPassword()));
    }
}
