package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.holovin.smidatestproject.utils.RandomUtils.createRandomCompany;
import static com.holovin.smidatestproject.utils.RandomUtils.createRandomReportBigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        reportRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void shouldReturnReportsWhenCallFindAllByCompanyId() {
        // Given
        Company saveCompany1 = companyRepository.save(createRandomCompany());
        Company saveCompany2 = companyRepository.save(createRandomCompany());

        Report report1 = createRandomReportBigDecimal(saveCompany1);
        Report report2 = createRandomReportBigDecimal(saveCompany1);
        Report report3 = createRandomReportBigDecimal(saveCompany2);

        reportRepository.save(report1);
        reportRepository.save(report2);
        reportRepository.save(report3);

        // When
        List<Report> actualReports = reportRepository.findAllByCompanyId(saveCompany1.getId());

        // Then
        assertThat(actualReports).hasSize(2);
        assertReport(actualReports.get(0), report1);
        assertReport(actualReports.get(1), report2);
    }

    @Test
    public void shouldReturnReportWhenCallFindById() {
        // Given
        Company saveCompany = companyRepository.save(createRandomCompany());
        Report report = createRandomReportBigDecimal(saveCompany);
        Report expectedReport = reportRepository.save(report);

        // When
        Optional<Report> actualOptionalReport = reportRepository.findById(expectedReport.getId());

        // Then
        assertThat(actualOptionalReport).isPresent();
        assertReport(actualOptionalReport.get(), report);
    }

    @Test
    public void shouldDeleteReportWhenCallDeleteById() {
        // Given
        Company saveCompany = companyRepository.save(createRandomCompany());
        Report report = createRandomReportBigDecimal(saveCompany);
        Report expectedReport = reportRepository.save(report);

        // When
        reportRepository.deleteById(expectedReport.getId());
        Optional<Report> actualOptionalReport = reportRepository.findById(expectedReport.getId());

        // Then
        assertThat(actualOptionalReport).isNotPresent();
    }

    private static void assertReport(Report actualReport, Report expectedReport) {
        assertThat(actualReport.getCompany().getId()).isEqualTo(expectedReport.getCompany().getId());
        assertThat(actualReport.getReportDate()).isEqualTo(expectedReport.getReportDate());
        assertThat(expectedReport.getTotalRevenue().intValue()).isEqualTo(actualReport.getTotalRevenue().intValue());
        assertThat(actualReport.getNetProfit().intValue()).isEqualTo(expectedReport.getNetProfit().intValue());
    }
}
