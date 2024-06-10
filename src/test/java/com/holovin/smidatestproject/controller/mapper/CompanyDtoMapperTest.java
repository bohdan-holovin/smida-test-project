package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.CompanyCreateRequestDto;
import com.holovin.smidatestproject.controller.dto.request.CompanyUpdateRequestDto;
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
        List<CompanyResponseDto> expected = testCompanies.stream()
                .map(company -> new CompanyResponseDto(
                        company.getId(),
                        company.getName(),
                        company.getRegistrationNumber(),
                        company.getAddress(),
                        company.getCreatedAt()
                ))
                .collect(Collectors.toList());

        // When
        List<CompanyResponseDto> actual = CompanyDtoMapper.toCompanyResponseDtoList(testCompanies);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldMapCompanyCreateRequestDtoToCompany() {
        // Given
        Company expectedCompany = RandomUtils.createCompany();
        CompanyCreateRequestDto requestDto = new CompanyCreateRequestDto();
        requestDto.setName(expectedCompany.getName());
        requestDto.setRegistrationNumber(expectedCompany.getRegistrationNumber());
        requestDto.setAddress(expectedCompany.getAddress());


        // When
        Company actualCompany = CompanyDtoMapper.toCompany(requestDto);

        // Then
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(expectedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(expectedCompany.getAddress());
    }

    @Test
    void shouldMapCompanyUpdateRequestDtoToCompany() {
        // Given
        Company expectedCompany = RandomUtils.createCompany();
        CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto();
        requestDto.setId(expectedCompany.getId());
        requestDto.setName(expectedCompany.getName());
        requestDto.setRegistrationNumber(expectedCompany.getRegistrationNumber());
        requestDto.setAddress(expectedCompany.getAddress());

        // When
        Company actualCompany = CompanyDtoMapper.toCompany(requestDto);

        // Then
        assertThat(actualCompany.getId()).isEqualTo(expectedCompany.getId());
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(expectedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(expectedCompany.getAddress());
    }
}
