package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import com.holovin.smidatestproject.exception.CompanyNotFoundException;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.repository.CompanyRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyServiceTest extends AbstractUnitTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = RandomUtils.createCompany();
    }

    @Test
    void shouldReturnCompaniesWhenCallAllCompaniesCompanies() {
        // Given
        List<Company> expectedCompanies = List.of(testCompany);
        when(companyRepository.findAll()).thenReturn(expectedCompanies);

        // When
        List<Company> actualCompanies = companyService.getAllCompanies();

        // Then
        assertThat(actualCompanies).isEqualTo(expectedCompanies);
        verify(companyRepository).findAll();
    }

    @Test
    void shouldReturnCompanyWhenCallFindByCompanyId() {
        // Given
        UUID companyId = testCompany.getId();
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(testCompany));

        // When
        Company actualCompany = companyService.getCompanyByCompanyId(companyId);

        // Then
        assertThat(actualCompany).isEqualTo(testCompany);
        verify(companyRepository).findById(companyId);
    }

    @Test
    void shouldThrowCompanyNotFoundExceptionWhenCallFindByCompanyId() {
        // Given
        UUID id = UUID.randomUUID();
        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompanyByCompanyId(id));
        verify(companyRepository).findById(id);
    }

    @Test
    void shouldReturnCompanyWhenCallCreateUpdateCompany() {
        // Given
        when(companyRepository.save(testCompany)).thenReturn(testCompany);

        // When
        Company actualCompany = companyService.createUpdate(testCompany);

        // Then
        assertThat(actualCompany).isEqualTo(testCompany);
        verify(companyRepository).save(testCompany);
    }

    @Test
    void shouldReturnUpdatedCompanyWhenCallUpdateCompany() {
        // Given
        UUID id = testCompany.getId();
        Company expectedCompany = RandomUtils.createCompany();
        expectedCompany.setId(id);

        when(companyRepository.findById(id)).thenReturn(Optional.of(testCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(expectedCompany);

        // When
        Company actualCompany = companyService.updateCompany(expectedCompany);

        // Then
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(expectedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(expectedCompany.getAddress());

        verify(companyRepository).findById(id);
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void shouldThrowCompanyNotFoundExceptionWhenCallUpdateCompany() {
        // Given
        UUID id = testCompany.getId();
        Company updatedCompany = RandomUtils.createCompany();
        updatedCompany.setId(id);

        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> companyService.updateCompany(updatedCompany));
        verify(companyRepository).findById(id);
    }

    @Test
    void shouldDeleteCompany() {
        // Given
        UUID id = testCompany.getId();
        when(companyRepository.findById(id)).thenReturn(Optional.ofNullable(testCompany));
        doNothing().when(companyRepository).deleteById(id);

        // When
        companyService.deleteCompanyByCompanyId(id);

        // Then
        verify(companyRepository).deleteById(id);
    }
}
