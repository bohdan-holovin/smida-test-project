package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.controller.dto.response.CompanyResponseDto;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.controller.mapper.CompanyMapper.toCompanyResponseDto;
import static com.holovin.smidatestproject.controller.mapper.CompanyMapper.toCompanyResponseDtoList;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final CompanyService companyService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        return ResponseEntity.ok(toCompanyResponseDtoList(companyService.getAllCompanies()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable UUID id) {
        return ResponseEntity.ok(toCompanyResponseDto(companyService.getCompanyByCompanyId(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody Company company) {
        return ResponseEntity.ok(toCompanyResponseDto(companyService.createUpdate(company)));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CompanyResponseDto> updateCompany(@RequestBody Company updatedCompany) {
        return ResponseEntity.ok(toCompanyResponseDto(companyService.updateCompany(updatedCompany)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID id) {
        companyService.getCompanyByCompanyId(id);
        companyService.deleteCompanyByCompanyId(id);
        return ResponseEntity.ok().build();
    }
}
