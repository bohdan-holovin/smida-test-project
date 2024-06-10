//package com.holovin.smidatestproject.service.mapper;
//
//import com.holovin.smidatestproject.model.Company;
//import com.holovin.smidatestproject.model.FullReport;
//import com.holovin.smidatestproject.model.Report;
//import com.holovin.smidatestproject.model.ReportDetails;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.time.chrono.ChronoLocalDate;
//import java.time.temporal.TemporalAccessor;
//import java.util.UUID;
//
//import static com.holovin.smidatestproject.utils.RandomUtils.createCompany;
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ReportMapperTest {
//
//    @Test
//    void shouldMapReportAndReportDetailsToFullReport() {
//        // Given
//        Company company = createCompany();
//        UUID reportId = company.getId();
//
//        Report report = new Report();
//        report.setId(reportId);
//        report.setCompany(company);
//        report.setReportDate(Timestamp.from(Instant.from(new ChronoLocalDateImpl() {
//        })));
//        report.setTotalRevenue(BigDecimal.valueOf(1000.0));
//        report.setNetProfit(BigDecimal.valueOf(200.0));
//
//        ReportDetails reportDetails = new ReportDetails();
//        reportDetails.setReportId(reportId);
//        reportDetails.setFinancialData("Sample Financial Data");
//        reportDetails.setComments("Sample Comments");
//
//        FullReport expectedFullReport = new FullReport();
//        expectedFullReport.setId(report.getId());
//        expectedFullReport.setCompany(report.getCompany());
//        expectedFullReport.setReportDate(report.getReportDate());
//        expectedFullReport.setTotalRevenue(report.getTotalRevenue());
//        expectedFullReport.setNetProfit(report.getNetProfit());
//        expectedFullReport.setFinancialData(reportDetails.getFinancialData());
//        expectedFullReport.setComments(reportDetails.getFinancialData()); // тут помилка в оригінальному коді
//
//        // When
//        FullReport actualFullReport = ReportMapper.toFullReport(report, reportDetails);
//
//        // Then
//        assertThat(actualFullReport).isEqualTo(expectedFullReport);
//    }
////
////    @Test
////    void shouldMapFullReportToReport() {
////        // Given
////        UUID reportId = UUID.randomUUID();
////        FullReport fullReport = new FullReport();
////        fullReport.setId(reportId);
////        fullReport.setCompany("Test Company");
////        fullReport.setReportDate("2024-01-01");
////        fullReport.setTotalRevenue(1000.0);
////        fullReport.setNetProfit(200.0);
////        fullReport.setFinancialData("Sample Financial Data");
////        fullReport.setComments("Sample Comments");
////
////        Report expectedReport = new Report();
////        expectedReport.setId(fullReport.getId());
////        expectedReport.setCompany(fullReport.getCompany());
////        expectedReport.setReportDate(fullReport.getReportDate());
////        expectedReport.setTotalRevenue(fullReport.getTotalRevenue());
////        expectedReport.setNetProfit(fullReport.getNetProfit());
////
////        // When
////        Report actualReport = ReportMapper.toReport(fullReport);
////
////        // Then
////        assertThat(actualReport).isEqualTo(expectedReport);
////    }
////
////    @Test
////    void shouldMapFullReportToReportDetails() {
////        // Given
////        UUID reportId = UUID.randomUUID();
////        FullReport fullReport = new FullReport();
////        fullReport.setId(reportId);
////        fullReport.setCompany("Test Company");
////        fullReport.setReportDate("2024-01-01");
////        fullReport.setTotalRevenue(1000.0);
////        fullReport.setNetProfit(200.0);
////        fullReport.setFinancialData("Sample Financial Data");
////        fullReport.setComments("Sample Comments");
////
////        ReportDetails expectedReportDetails = new ReportDetails();
////        expectedReportDetails.setReportId(fullReport.getId());
////        expectedReportDetails.setFinancialData(fullReport.getFinancialData());
////        expectedReportDetails.setComments(fullReport.getFinancialData()); // тут помилка в оригінальному коді
////
////        // When
////        ReportDetails actualReportDetails = ReportMapper.toReportDetails(fullReport);
////
////        // Then
////        assertThat(actualReportDetails).isEqualTo(expectedReportDetails);
////    }
//}
