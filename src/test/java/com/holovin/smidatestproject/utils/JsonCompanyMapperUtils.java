package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.company.request.CreateCompanyRequestDto;
import com.holovin.smidatestproject.model.Company;

public class JsonCompanyMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonCreateCompanyRequestDto(Company company) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new CreateCompanyRequestDto(
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress()
                )
        );
    }

    public static String toJsonUpdateCompanyRequestDto(Company company) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new CreateCompanyRequestDto(
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress()
                )
        );
    }
}
