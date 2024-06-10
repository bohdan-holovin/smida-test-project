package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.request.CompanyCreateRequestDto;
import com.holovin.smidatestproject.model.Company;

public class JsonCompanyMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonCompanyCreateDto(Company company) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new CompanyCreateRequestDto(
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress()
                )
        );
    }

    public static String toJsonCompanyUpdateDto(Company company) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new CompanyCreateRequestDto(
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress()
                )
        );
    }
}
