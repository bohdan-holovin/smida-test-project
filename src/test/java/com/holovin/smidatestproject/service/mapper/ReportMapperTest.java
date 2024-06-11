package com.holovin.smidatestproject.service.mapper;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import org.junit.jupiter.api.Test;

import static com.holovin.smidatestproject.utils.RandomUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReportMapperTest {

    @Test
    void shouldMapReportAndReportDetailsToFullReport() {
        // Given
        Company company = createCompany();

        Report report = createRandomReport(company);
        ReportDetails reportDetails = createRandomReportDetails();

        FullReport expectedFullReport = new FullReport();
        expectedFullReport.setId(report.getId());
        expectedFullReport.setCompany(report.getCompany());
        expectedFullReport.setReportDate(report.getReportDate());
        expectedFullReport.setTotalRevenue(report.getTotalRevenue());
        expectedFullReport.setNetProfit(report.getNetProfit());
        expectedFullReport.setFinancialData(reportDetails.getFinancialData());
        expectedFullReport.setComments(reportDetails.getComments()); // тут помилка в оригінальному коді

        // When
        FullReport actualFullReport = ReportMapper.toFullReport(report, reportDetails);

        // Then
        assertThat(actualFullReport).isEqualTo(expectedFullReport);
    }

    @Test
    void shouldMapFullReportToReport() {
        // Given
        FullReport fullReport = createRandomFullReport();

        Report expectedReport = new Report();
        expectedReport.setId(fullReport.getId());
        expectedReport.setCompany(fullReport.getCompany());
        expectedReport.setReportDate(fullReport.getReportDate());
        expectedReport.setTotalRevenue(fullReport.getTotalRevenue());
        expectedReport.setNetProfit(fullReport.getNetProfit());

        // When
        Report actualReport = ReportMapper.toReport(fullReport);

        // Then
        assertThat(actualReport).isEqualTo(expectedReport);
    }

    @Test
    void shouldMapFullReportToReportDetails() {
        // Given
        FullReport fullReport = createRandomFullReport();

        ReportDetails expectedReportDetails = new ReportDetails();
        expectedReportDetails.setReportId(fullReport.getId());
        expectedReportDetails.setFinancialData(fullReport.getFinancialData());
        expectedReportDetails.setComments(fullReport.getComments());

        // When
        ReportDetails actualReportDetails = ReportMapper.toReportDetails(fullReport);

        // Then
        assertThat(actualReportDetails).isEqualTo(expectedReportDetails);
    }
}
