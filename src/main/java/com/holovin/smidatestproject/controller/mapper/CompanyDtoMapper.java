package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.response.CompanyResponseDto;
import com.holovin.smidatestproject.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyDtoMapper {


    public static CompanyResponseDto toCompanyResponseDto(Company company) {
        return new CompanyResponseDto(
                company.getId(),
                company.getName(),
                company.getRegistrationNumber(),
                company.getAddress(),
                company.getCreatedAt()
        );
    }

    public static List<CompanyResponseDto> toCompanyResponseDtoList(List<Company> companies) {
        return companies.stream()
                .map(CompanyDtoMapper::toCompanyResponseDto)
                .collect(Collectors.toList());
    }
}
