package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.response.CompanyResponseDto;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyDtoMapperTest {

    @Test
    void shouldMapCompanyToCompanyResponseDto() {
        // Given
        Company testCompany = RandomUtils.createCompany();
        CompanyResponseDto expectedDto = new CompanyResponseDto(
                testCompany.getId(),
                testCompany.getName(),
                testCompany.getRegistrationNumber(),
                testCompany.getAddress(),
                testCompany.getCreatedAt()
        );

        // When
        CompanyResponseDto actualDto = CompanyDtoMapper.toCompanyResponseDto(testCompany);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void shouldMapCompanyListToCompanyResponseDtoList() {
        // Given
        List<Company> testCompanies = List.of(RandomUtils.createCompany(), RandomUtils.createCompany());
        List<CompanyResponseDto> expectedDtos = testCompanies.stream()
                .map(company -> new CompanyResponseDto(
                        company.getId(),
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress(),
                        company.getCreatedAt()
                ))
                .collect(Collectors.toList());

        // When
        List<CompanyResponseDto> actualDtos = CompanyDtoMapper.toCompanyResponseDtoList(testCompanies);

        // Then
        assertThat(actualDtos).isEqualTo(expectedDtos);
    }
}
