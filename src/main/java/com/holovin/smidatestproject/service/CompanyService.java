package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exceptions.CompanyNotFoundException;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(UUID id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(UUID id, Company updatedCompany) {
        return companyRepository.findById(id).map(company -> {
            company.setName(updatedCompany.getName());
            company.setRegistrationNumber(updatedCompany.getRegistrationNumber());
            company.setAddress(updatedCompany.getAddress());
            return companyRepository.save(company);
        }).orElseThrow(() -> new CompanyNotFoundException(id));
    }

    public void deleteById(UUID id) {
        companyRepository.deleteById(id);
    }
}
