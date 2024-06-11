package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import com.holovin.smidatestproject.exception.ReportDetailsNotFoundException;
import com.holovin.smidatestproject.exception.ReportNotFoundException;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.repository.ReportDetailsRepository;
import com.holovin.smidatestproject.repository.ReportRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.holovin.smidatestproject.service.mapper.ReportMapper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportServiceTest extends AbstractUnitTest {

    @Mock
    private CompanyService companyService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReportDetailsRepository reportDetailsRepository;

    @InjectMocks
    private ReportService reportService;

    private Report testReport;
    private ReportDetails testReportDetails;
    private FullReport testFullReport;

    @BeforeEach
    void setUp() {
        testReport = RandomUtils.createRandomReport(RandomUtils.createRandomCompany());
        testReportDetails = RandomUtils.createRandomReportDetails();
        testFullReport = toFullReport(testReport, testReportDetails);
    }

    @Test
    void shouldReturnAllReports() {
        // Given
        List<Report> expectedReports = List.of(testReport);
        when(reportRepository.findAll()).thenReturn(expectedReports);

        // When
        List<Report> actualReports = reportService.getAllReports();

        // Then
        assertThat(actualReports).isEqualTo(expectedReports);
        verify(reportRepository).findAll();
    }

    @Test
    void shouldReturnReportsByCompanyId() {
        // Given
        List<Report> expectedReports = List.of(testReport);
        UUID companyId = testReport.getCompany().getId();
        when(companyService.getCompanyByCompanyId(companyId)).thenReturn(testReport.getCompany());
        when(reportRepository.findAllByCompanyId(companyId)).thenReturn(expectedReports);

        // When
        List<Report> actualReports = reportService.getAllReportsByCompanyId(companyId);

        // Then
        assertThat(actualReports).isEqualTo(expectedReports);
        verify(companyService).getCompanyByCompanyId(companyId);
        verify(reportRepository).findAllByCompanyId(companyId);
    }

    @Test
    void shouldReturnReportByReportId() {
        // Given
        UUID reportId = testReport.getId();
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));

        // When
        Report actualReport = reportService.getReportByReportId(reportId);

        // Then
        assertThat(actualReport).isEqualTo(testReport);
        verify(reportRepository).findById(reportId);
    }

    @Test
    void shouldThrowReportNotFoundExceptionWhenReportByIdNotFound() {
        // Given
        UUID reportId = UUID.randomUUID();
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ReportNotFoundException.class, () -> reportService.getReportByReportId(reportId));
        verify(reportRepository).findById(reportId);
    }

    @Test
    void shouldReturnReportDetailsByReportId() {
        // Given
        UUID reportId = testReportDetails.getReportId();
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));

        // When
        ReportDetails actualReportDetails = reportService.getReportDetailByReportId(reportId);

        // Then
        assertThat(actualReportDetails).isEqualTo(testReportDetails);
        verify(reportDetailsRepository).findById(reportId);
    }

    @Test
    void shouldThrowReportDetailsNotFoundExceptionWhenReportDetailsByIdNotFound() {
        // Given
        UUID reportId = UUID.randomUUID();
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ReportDetailsNotFoundException.class, () -> reportService.getReportDetailByReportId(reportId));
        verify(reportDetailsRepository).findById(reportId);
    }

    @Test
    void shouldReturnFullReportByReportId() {
        // Given
        UUID reportId = testReport.getId();
        testReportDetails.setReportId(testReport.getId());
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));

        // When
        FullReport actualFullReport = reportService.getFullReportByReportId(reportId);

        // Then
        assertThat(actualFullReport).isEqualTo(toFullReport(testReport, testReportDetails));
        verify(reportRepository).findById(reportId);
        verify(reportDetailsRepository).findById(reportId);
    }

    @Test
    void shouldCreateReport() {
        // Given
        when(companyService.getCompanyByCompanyId(testReport.getId())).thenReturn(testReport.getCompany());
        when(reportRepository.save(testReport)).thenReturn(testReport);

        // When
        Report actualReport = reportService.createReport(testReport.getId(), testReport);

        // Then
        assertThat(actualReport).isEqualTo(testReport);
        verify(companyService).getCompanyByCompanyId(testReport.getId());
        verify(reportRepository).save(testReport);
    }

    @Test
    void shouldCreateReportDetails() {
        // Given
        UUID reportId = testReport.getId();
        testReportDetails.setReportId(testReport.getId());
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportDetailsRepository.save(testReportDetails)).thenReturn(testReportDetails);

        // When
        ReportDetails actualReportDetails = reportService.createReportDetails(testReportDetails);

        // Then
        assertThat(actualReportDetails).isEqualTo(testReportDetails);
        verify(reportRepository).findById(reportId);
        verify(reportDetailsRepository).save(testReportDetails);
    }

    @Test
    void shouldCreateFullReport() {
        // Given
        testReportDetails.setReportId(testReport.getId());

        when(companyService.getCompanyByCompanyId(testReport.getCompany().getId())).thenReturn(testReport.getCompany());
        when(reportRepository.save(any(Report.class))).thenReturn(testReport);
        when(reportRepository.findById(testReport.getId())).thenReturn(Optional.of(testReport));
        when(reportDetailsRepository.save(any(ReportDetails.class))).thenReturn(testReportDetails);
        FullReport expectedFullReport = toFullReport(testReport, testReportDetails);

        // When
        FullReport actualFullReport = reportService.createFullReport(testReport.getCompany().getId(), expectedFullReport);

        // Then
        assertThat(actualFullReport).isEqualTo(expectedFullReport);
        verify(companyService).getCompanyByCompanyId(testReport.getCompany().getId());
        verify(reportRepository).save(any(Report.class));
        verify(reportRepository).findById(testReport.getId());
        verify(reportDetailsRepository).save(any(ReportDetails.class));
    }

    @Test
    void shouldUpdateReport() {
        // Given
        UUID reportId = testReport.getId();
        Report expectedReport = toReport(testFullReport);
        expectedReport.setId(reportId);

        when(companyService.getCompanyByCompanyId(testReport.getCompany().getId())).thenReturn(testReport.getCompany());
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportRepository.save(expectedReport)).thenReturn(expectedReport);

        // When
        Report actualReport = reportService.updateReport(testFullReport.getCompany().getId(), expectedReport);

        // Then
        verify(companyService).getCompanyByCompanyId(testReport.getCompany().getId());
        assertThat(actualReport).isEqualTo(expectedReport);
        verify(reportRepository).findById(reportId);
        verify(reportRepository).save(any(Report.class));
    }

    @Test
    void shouldUpdateReportDetails() {
        // Given
        UUID reportId = testReportDetails.getReportId();
        ReportDetails expectedReportDetails = toReportDetails(testFullReport);
        expectedReportDetails.setReportId(reportId);

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));
        when(reportDetailsRepository.save(testReportDetails)).thenReturn(expectedReportDetails);

        // When
        ReportDetails actualReportDetails = reportService.updateReportDetails(testReportDetails);

        // Then
        assertThat(actualReportDetails).isEqualTo(expectedReportDetails);
        verify(reportRepository).findById(reportId);
        verify(reportDetailsRepository).findById(reportId);
        verify(reportDetailsRepository).save(any(ReportDetails.class));
    }

    @Test
    void shouldUpdateFullReport() {
        // Given
        UUID reportId = testFullReport.getId();
        Report updatedReport = toReport(testFullReport);
        ReportDetails reportDetails = toReportDetails(testFullReport);

        FullReport expectedReportDetails = toFullReport(testReport, testReportDetails);

        when(companyService.getCompanyByCompanyId(testReport.getCompany().getId())).thenReturn(testReport.getCompany());
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportRepository.save(testReport)).thenReturn(updatedReport);
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));
        when(reportDetailsRepository.save(testReportDetails)).thenReturn(reportDetails);

        // When
        FullReport actualReportDetails = reportService.updateFullReport(updatedReport.getCompany().getId(), testFullReport);

        // Then
        assertThat(actualReportDetails).isEqualTo(expectedReportDetails);
        verify(companyService).getCompanyByCompanyId(testReport.getCompany().getId());
        verify(reportRepository, times(2)).findById(reportId);
        verify(reportRepository).save(testReport);
        verify(reportDetailsRepository).findById(reportId);
        verify(reportDetailsRepository).save(any(ReportDetails.class));
    }

    @Test
    void shouldCascadeDeleteReportByReportId() {
        // Given
        UUID reportId = testReport.getId();
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        doNothing().when(reportDetailsRepository).deleteById(reportId);
        doNothing().when(reportRepository).deleteById(reportId);

        // When
        reportService.cascadeDeleteReportByReportId(reportId);

        // Then
        verify(reportRepository).findById(reportId);
        verify(reportDetailsRepository).findById(reportId);
        verify(reportRepository).deleteById(reportId);
        verify(reportDetailsRepository).deleteById(reportId);
    }

    @Test
    void shouldDeleteReportDetailsByReportId() {
        // Given
        UUID reportId = testReportDetails.getReportId();
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(testReport));
        when(reportDetailsRepository.findById(reportId)).thenReturn(Optional.of(testReportDetails));
        doNothing().when(reportDetailsRepository).deleteById(reportId);

        // When
        reportService.deleteReportDetailsByReportId(reportId);

        // Then
        verify(reportRepository).findById(reportId);
        verify(reportDetailsRepository).findById(reportId);
        verify(reportDetailsRepository).deleteById(reportId);
    }

    @Test
    void shouldCascadeDeleteAllReportByReportId() {
        // Given
        UUID reportId = testReport.getId();
        UUID reportId2 = UUID.randomUUID();
        List<UUID> reportIdList = List.of(reportId, reportId2);

        testReportDetails.setReportId(reportId);

        doNothing().when(reportDetailsRepository).deleteAllById(reportIdList);
        doNothing().when(reportRepository).deleteAllById(reportIdList);

        // When
        reportService.cascadeDeleteAllReportByReportId(reportIdList);

        // Then
        verify(reportRepository).deleteAllById(reportIdList);
        verify(reportDetailsRepository).deleteAllById(reportIdList);
    }
}
