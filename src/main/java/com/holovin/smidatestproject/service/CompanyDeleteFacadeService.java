package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.model.Report;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyDeleteFacadeService {

    private ReportService reportService;
    private CompanyService companyService;

    public void cascadeDeleteCompany(UUID companyId) {
        List<UUID> allReportsByCompanyId = reportService.getAllReportsByCompanyId(companyId)
                .stream().map(Report::getId)
                .collect(Collectors.toList());
        reportService.cascadeDeleteAllReportByReportId(allReportsByCompanyId);
        companyService.deleteCompanyByCompanyId(companyId);
    }
}
