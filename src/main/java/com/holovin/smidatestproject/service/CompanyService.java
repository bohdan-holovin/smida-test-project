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

    public Company updateCompany(Company updatedCompany) {
        return companyRepository.findById(updatedCompany.getId()).map(company -> {
            company.setName(updatedCompany.getName());
            company.setRegistrationNumber(updatedCompany.getRegistrationNumber());
            company.setAddress(updatedCompany.getAddress());
            return companyRepository.save(company);
        }).orElseThrow(() -> new CompanyNotFoundException(updatedCompany.getId()));
    }

    public void deleteById(UUID id) {
        findById(id);
        companyRepository.deleteById(id);
    }
}
