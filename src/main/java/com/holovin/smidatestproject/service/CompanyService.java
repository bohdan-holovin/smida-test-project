package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exception.CompanyNotFoundException;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyByCompanyId(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public Company createUpdate(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Company updatedCompany) {
        return Optional.of(getCompanyByCompanyId(updatedCompany.getId()))
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setRegistrationNumber(updatedCompany.getRegistrationNumber());
                    company.setAddress(updatedCompany.getAddress());
                    return company;
                })
                .map(companyRepository::save)
                .orElseThrow(() -> new CompanyNotFoundException(updatedCompany.getId()));
    }

    void deleteCompanyByCompanyId(UUID id) {
        getCompanyByCompanyId(id);
        companyRepository.deleteById(id);
    }
}
