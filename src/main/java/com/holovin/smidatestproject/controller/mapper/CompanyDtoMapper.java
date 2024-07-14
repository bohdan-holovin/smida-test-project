package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.company.request.CreateCompanyRequestDto;
import com.holovin.smidatestproject.controller.dto.company.request.UpdateCompanyRequestDto;
import com.holovin.smidatestproject.controller.dto.company.response.CompanyResponseDto;
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

    public static Company toCompany(CreateCompanyRequestDto createRequestDto) {
        Company company = new Company();
        company.setName(createRequestDto.getName());
        company.setAddress(createRequestDto.getAddress());
        company.setRegistrationNumber(createRequestDto.getRegistrationNumber());
        return company;
    }

    public static Company toCompany(UpdateCompanyRequestDto updateRequestDto) {
        Company company = new Company();
        company.setId(updateRequestDto.getId());
        company.setName(updateRequestDto.getName());
        company.setAddress(updateRequestDto.getAddress());
        company.setRegistrationNumber(updateRequestDto.getRegistrationNumber());
        return company;
    }
}
