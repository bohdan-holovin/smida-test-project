package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exceptions.CompanyNotFoundException;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.repository.CompanyRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

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
    void shouldGiveCompaniesWhenUseFindAll() {
        // Given
        List<Company> expectedCompanies = List.of(testCompany);
        when(companyRepository.findAll()).thenReturn(expectedCompanies);

        // When
        List<Company> actualCompanies = companyService.findAll();

        // Then
        assertThat(actualCompanies).isEqualTo(expectedCompanies);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void shouldGiveCompanyWhenUseFindById() {
        // Given
        UUID companyId = testCompany.getId();
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(testCompany));

        // When
        Company actualCompany = companyService.findById(companyId);

        // Then
        assertThat(actualCompany).isEqualTo(testCompany);
        verify(companyRepository, times(1)).findById(companyId);
    }

    @Test
    void shouldThrowCompanyNotFoundExceptionWhenUseFindById() {
        // Given
        UUID id = UUID.randomUUID();
        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> companyService.findById(id));
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void shouldGiveCompanyWhenUseCreate() {
        // Given
        when(companyRepository.save(testCompany)).thenReturn(testCompany);

        // When
        Company createdCompany = companyService.create(testCompany);

        // Then
        assertThat(createdCompany).isEqualTo(testCompany);
        verify(companyRepository, times(1)).save(testCompany);
    }

    @Test
    void shouldGiveUpdatedCompanyWhenUseUpdate() {
        // Given
        UUID id = testCompany.getId();
        Company updatedCompany = RandomUtils.createCompany();
        when(companyRepository.findById(id)).thenReturn(Optional.of(testCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(updatedCompany);

        // When
        Company actualCompany = companyService.updateCompany(updatedCompany);

        // Then
        assertThat(actualCompany.getName()).isEqualTo(updatedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(updatedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(updatedCompany.getAddress());
        verify(companyRepository, times(1)).findById(id);
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void shouldThrowCompanyNotFoundExceptionWhenUseUpdate() {
        // Given
        UUID id = UUID.randomUUID();
        Company updatedCompany = RandomUtils.createCompany();
        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> companyService.updateCompany(updatedCompany));
        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void shouldUseRepositoryWhenUseDelete() {
        // Given
        UUID id = testCompany.getId();
        doNothing().when(companyRepository).deleteById(id);

        // When
        companyService.deleteById(id);

        // Then
        verify(companyRepository, times(1)).deleteById(id);
    }
}
