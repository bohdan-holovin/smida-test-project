package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        companyRepository.deleteAll();
    }

    @Test
    public void shouldReturnCompaniesWhenCallFindAll() {
        // Given
        Company company1 = RandomUtils.createCompany();
        Company company2 = RandomUtils.createCompany();

        companyRepository.save(company1);
        companyRepository.save(company2);

        // When
        List<Company> actualCompanies = companyRepository.findAll();

        // Then
        assertThat(actualCompanies).hasSize(2);
        assertCompany(actualCompanies.get(0), company1);
        assertCompany(actualCompanies.get(1), company2);
    }

    @Test
    public void shouldReturnCompanyWhenCallFindById() {
        // Given
        Company company = RandomUtils.createCompany();
        Company expectedCompany = companyRepository.save(company);

        // When
        Optional<Company> actualOptionalCompany = companyRepository.findById(expectedCompany.getId());

        // Then
        assertThat(actualOptionalCompany).isPresent();
        assertCompany(actualOptionalCompany.get(), expectedCompany);
    }


    @Test
    public void shouldDeleteCompanyWhenCallDeleteById() {
        // Given
        Company company = RandomUtils.createCompany();
        companyRepository.save(company);

        // When
        companyRepository.deleteById(company.getId());
        Optional<Company> actualOptionalCompany = companyRepository.findById(company.getId());

        // Then
        assertThat(actualOptionalCompany).isNotPresent();
    }

    private static void assertCompany(Company actualCompany, Company expectedCompany) {
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(expectedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(expectedCompany.getAddress());
    }
}
