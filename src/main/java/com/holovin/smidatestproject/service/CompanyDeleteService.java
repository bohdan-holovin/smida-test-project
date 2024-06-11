package com.holovin.smidatestproject.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CompanyDeleteService {

    private ReportService reportService;
    private CompanyService companyService;

    public void cascadeCompanyDelete(UUID companyId) {
        reportService.getAllReportsByCompanyId(companyId)
                .forEach(report -> reportService.cascadeDeleteReportByReportId(report.getId()));
        companyService.deleteCompanyByCompanyId(companyId);
    }
}
