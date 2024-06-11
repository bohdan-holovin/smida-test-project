package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.controller.dto.company.request.CompanyCreateRequestDto;
import com.holovin.smidatestproject.controller.dto.company.request.CompanyUpdateRequestDto;
import com.holovin.smidatestproject.controller.dto.company.response.CompanyResponseDto;
import com.holovin.smidatestproject.service.CompanyDeleteService;
import com.holovin.smidatestproject.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.controller.mapper.CompanyDtoMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDeleteService companyDeleteService;
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        logger.info("Fetching all companies");
        List<CompanyResponseDto> companies = toCompanyResponseDtoList(companyService.getAllCompanies());
        logger.info("Found {} companies", companies.size());
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable UUID id) {
        logger.info("Fetching company with id {}", id);
        CompanyResponseDto company = toCompanyResponseDto(companyService.getCompanyByCompanyId(id));
        logger.info("Found company response: {}", company);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CompanyResponseDto> createCompany(@Valid @RequestBody CompanyCreateRequestDto company) {
        logger.info("Creating company request: {}", company);
        CompanyResponseDto createdCompany = toCompanyResponseDto(companyService.createUpdate(toCompany(company)));
        logger.info("Created company response: {}", createdCompany);
        return ResponseEntity.ok(createdCompany);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CompanyResponseDto> updateCompany(@Valid @RequestBody CompanyUpdateRequestDto company) {
        logger.info("Updating company request: {}", company);
        CompanyResponseDto updatedCompany = toCompanyResponseDto(companyService.updateCompany(toCompany(company)));
        logger.info("Updated company response: {}", updatedCompany);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {
        logger.info("Deleting company with id {}", id);
        companyDeleteService.cascadeCompanyDelete(id);
        logger.info("Deleted company with id {}", id);
        return ResponseEntity.ok().build();
    }
}
