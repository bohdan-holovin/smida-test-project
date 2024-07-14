package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.ReportDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.holovin.smidatestproject.utils.RandomUtils.createRandomReportDetails;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReportDetailsRepositoryTest {

    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

    @AfterEach
    public void setUp() {
        reportDetailsRepository.deleteAll();
    }

    @Test
    public void shouldReturnReportDetailsWhenCallFindAll() {
        // Given
        ReportDetails reportDetails1 = createRandomReportDetails();
        ReportDetails reportDetails2 = createRandomReportDetails();

        reportDetailsRepository.save(reportDetails1);
        reportDetailsRepository.save(reportDetails2);

        // When
        List<ReportDetails> actualReportDetails = reportDetailsRepository.findAll();

        // Then
        assertThat(actualReportDetails).hasSize(2);
        assertReportDetails(actualReportDetails.get(0), reportDetails1);
        assertReportDetails(actualReportDetails.get(1), reportDetails2);
    }

    @Test
    public void shouldReturnReportDetailsWhenCallFindById() {
        // Given
        ReportDetails expectedReportDetails = createRandomReportDetails();
        reportDetailsRepository.save(expectedReportDetails);

        // When
        Optional<ReportDetails> actualOptionalReportDetails =
                reportDetailsRepository.findById(expectedReportDetails.getReportId());

        // Then
        assertThat(actualOptionalReportDetails).isPresent();
        assertReportDetails(actualOptionalReportDetails.get(), expectedReportDetails);
    }

    @Test
    public void shouldDeleteReportDetailsWhenCallDeleteById() {
        // Given
        ReportDetails reportDetails = createRandomReportDetails();
        reportDetailsRepository.save(reportDetails);

        // When
        reportDetailsRepository.deleteById(reportDetails.getReportId());
        Optional<ReportDetails> actualOptionalReportDetails =
                reportDetailsRepository.findById(reportDetails.getReportId());

        // Then
        assertThat(actualOptionalReportDetails).isNotPresent();
    }

    private static void assertReportDetails(ReportDetails actualReportDetails, ReportDetails expectedReportDetails) {
        assertThat(actualReportDetails.getReportId()).isEqualTo(expectedReportDetails.getReportId());
        assertThat(actualReportDetails.getComments()).isEqualTo(expectedReportDetails.getComments());
        assertThat(actualReportDetails.getFinancialData()).isEqualTo(expectedReportDetails.getFinancialData());
    }
}
