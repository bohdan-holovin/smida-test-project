package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Company> getCompanyById(@PathVariable UUID id) {
        Company company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Company createCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Company> updateCompany(@PathVariable UUID id, @RequestBody Company updatedCompany) {
        Company company = companyService.updateCompany(id, updatedCompany);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {
        companyService.findById(id);
        companyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
