package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.request.UserAuthRequestDto;
import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.User;

public class JsonUserMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonUserRegisterRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new UserRegisterRequestDto(user.getUsername(), user.getPassword(), user.getRoles())
        );
    }

    public static String toJsonUserAuthRequestDto(User user) throws JsonProcessingException {

        return objectMapper.writeValueAsString(new UserAuthRequestDto(user.getUsername(), user.getPassword()));
    }
}
