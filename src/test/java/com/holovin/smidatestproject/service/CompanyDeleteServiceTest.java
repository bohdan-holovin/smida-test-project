package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyDeleteServiceTest {

    @Mock
    private ReportService reportService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyDeleteService companyDeleteService;

    @Test
    void testCascadeCompanyDelete() {
        // Given
        Company company = RandomUtils.createCompany();
        UUID companyId = company.getId();
        Report report1 = RandomUtils.createReport(company);
        Report report2 = RandomUtils.createReport(company);

        when(reportService.getAllReportsByCompanyId(companyId)).thenReturn(Arrays.asList(report1, report2));

        // When
        companyDeleteService.cascadeCompanyDelete(companyId);

        // Then
        verify(reportService).getAllReportsByCompanyId(companyId);
        verify(reportService).cascadeDeleteReportByReportId(report1.getId());
        verify(reportService).cascadeDeleteReportByReportId(report2.getId());
        verify(companyService).deleteCompanyByCompanyId(companyId);
    }
}
