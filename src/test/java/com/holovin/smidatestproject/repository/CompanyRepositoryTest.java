package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.utils.RandomUtils;
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

    @Test
    public void shouldReturnCompaniesWhenCallFindAll() {
        // Given
        Company company1 = RandomUtils.createCompany();
        Company company2 = RandomUtils.createCompany();

        companyRepository.save(company1);
        companyRepository.save(company2);

        // When
        List<Company> result = companyRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertCompany(result.get(0), company1);
        assertCompany(result.get(1), company2);
    }

    @Test
    public void shouldReturnCompanyWhenCallFindById() {
        // Given
        Company company = RandomUtils.createCompany();
        Company savedCompany = companyRepository.save(company);

        // When
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        // Then
        assertThat(foundCompany).isPresent();
        assertCompany(foundCompany.get(), company);
    }


    @Test
    public void shouldDeleteCompanyWhenCallDeleteById() {
        // Given
        Company company = RandomUtils.createCompany();
        Company savedCompany = companyRepository.save(company);

        // When
        companyRepository.deleteById(savedCompany.getId());
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        // Then
        assertThat(foundCompany).isNotPresent();
    }

    private static void assertCompany(Company actualCompany, Company expectedCompany) {
        assertThat(actualCompany.getName()).isEqualTo(expectedCompany.getName());
        assertThat(actualCompany.getRegistrationNumber()).isEqualTo(expectedCompany.getRegistrationNumber());
        assertThat(actualCompany.getAddress()).isEqualTo(expectedCompany.getAddress());
    }
}
