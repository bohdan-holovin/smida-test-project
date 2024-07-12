package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.AbstractIntegratedTest;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.service.CompanyService;
import com.holovin.smidatestproject.service.ReportService;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.utils.JsonReportMapperUtils.*;
import static com.holovin.smidatestproject.utils.RandomUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest extends AbstractIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldReturnAllReportsForAdmin() throws Exception {
        // Given
        String endpoint = "/reports";
        Report report = createRandomReportBigDecimal(createRandomCompany());

        List<Report> reportList = List.of(report);
        when(reportService.getAllReports()).thenReturn(reportList);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(report.getId().toString())))
                .andExpect(jsonPath("$[0].totalRevenue", equalTo(report.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$[0].netProfit", equalTo(report.getNetProfit().intValue())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnAllReportsByCompanyIdForUser() throws Exception {
        // Given
        Company company = createRandomCompany();
        UUID companyId = company.getId();

        String endpoint = "/reports/company/" + companyId;

        Report report = createRandomReportBigDecimal(company);
        List<Report> reportList = List.of(report);
        when(reportService.getAllReportsByCompanyId(companyId)).thenReturn(reportList);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(report.getId().toString())))
                .andExpect(jsonPath("$[0].totalRevenue", equalTo(report.getTotalRevenue().setScale(0, RoundingMode.HALF_UP).intValue())))
                .andExpect(jsonPath("$[0].netProfit", equalTo(report.getNetProfit().setScale(0, RoundingMode.HALF_UP).intValue())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnReportByIdForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId;
        Report report = createRandomReportBigDecimal(createRandomCompany());
        when(reportService.getReportByReportId(reportId)).thenReturn(report);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(report.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(report.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(report.getNetProfit().intValue())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnReportDetailsByReportIdForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId + "/details";
        ReportDetails reportDetails = RandomUtils.createRandomReportDetails();
        when(reportService.getReportDetailByReportId(reportId)).thenReturn(reportDetails);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId", equalTo(reportDetails.getReportId().toString())))
                .andExpect(jsonPath("$.financialData", equalTo(reportDetails.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(reportDetails.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnFullReportByReportIdForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId + "/full";
        FullReport fullReport = createRandomFullReportBigDecimal();
        when(reportService.getFullReportByReportId(reportId)).thenReturn(fullReport);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(fullReport.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(fullReport.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(fullReport.getNetProfit().intValue())))
                .andExpect(jsonPath("$.financialData", equalTo(fullReport.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(fullReport.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldCreateReportForUser() throws Exception {
        // Given
        String endpoint = "/reports";
        Company company = createRandomCompany();
        Report report = createRandomReportBigDecimal(company);
        String jsonReportRequestDto = toJsonReportRequestDto(report);

        when(reportService.createReport(eq(company.getId()), any(Report.class))).thenReturn(report);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReportRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(report.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(report.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(report.getNetProfit().intValue())))
                .andReturn();
    }


    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldCreateReportDetailsForUser() throws Exception {
        // Given
        String endpoint = "/reports/details";
        ReportDetails reportDetails = createRandomReportDetails();
        String jsonReportDetailsRequestDto = toJsonReportDetailsRequestDto(reportDetails);

        when(reportService.createReportDetails(any(ReportDetails.class))).thenReturn(reportDetails);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReportDetailsRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId", equalTo(reportDetails.getReportId().toString())))
                .andExpect(jsonPath("$.financialData", equalTo(reportDetails.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(reportDetails.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldCreateFullReportForUser() throws Exception {
        // Given
        String endpoint = "/reports/full";
        FullReport fullReport = createRandomFullReportBigDecimal();
        String jsonReportDetailsRequestDto = toJsonFullReportRequestDto(fullReport);

        when(reportService.createFullReport(eq(fullReport.getId()), any(FullReport.class))).thenReturn(fullReport);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReportDetailsRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(fullReport.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(fullReport.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(fullReport.getNetProfit().intValue())))
                .andExpect(jsonPath("$.financialData", equalTo(fullReport.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(fullReport.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldUpdateReportForUser() throws Exception {
        // Given
        String endpoint = "/reports";
        Report report = createRandomReportBigDecimal(createRandomCompany());
        String jsonFullReportRequestDto = toJsonReportRequestDto(report);
        when(reportService.updateReport(eq(report.getCompany().getId()), any(Report.class))).thenReturn(report);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFullReportRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(report.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(report.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(report.getNetProfit().intValue())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldUpdateReportDetailsForUser() throws Exception {
        // Given
        String endpoint = "/reports/details";
        ReportDetails reportDetails = createRandomReportDetails();
        String jsonReportDetailsRequestDto = toJsonReportDetailsRequestDto(reportDetails);

        when(reportService.updateReportDetails(any(ReportDetails.class))).thenReturn(reportDetails);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReportDetailsRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId", equalTo(reportDetails.getReportId().toString())))
                .andExpect(jsonPath("$.financialData", equalTo(reportDetails.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(reportDetails.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldUpdateFullReportForUser() throws Exception {
        // Given
        String endpoint = "/reports/full";
        FullReport fullReport = createRandomFullReportBigDecimal();
        String jsonReportDetailsRequestDto = toJsonFullReportRequestDto(fullReport);

        when(reportService.updateFullReport(eq(fullReport.getCompany().getId()), any(FullReport.class)))
                .thenReturn(fullReport);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReportDetailsRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(fullReport.getId().toString())))
                .andExpect(jsonPath("$.totalRevenue", equalTo(fullReport.getTotalRevenue().intValue())))
                .andExpect(jsonPath("$.netProfit", equalTo(fullReport.getNetProfit().intValue())))
                .andExpect(jsonPath("$.financialData", equalTo(fullReport.getFinancialData())))
                .andExpect(jsonPath("$.comments", equalTo(fullReport.getComments())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldDeleteReportForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId;
        doNothing().when(reportService).cascadeDeleteReportByReportId(reportId);

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldDeleteReportDetailsForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId + "/details";
        Mockito.doNothing().when(reportService).deleteReportDetailsByReportId(reportId);

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldDeleteFullReportForUser() throws Exception {
        // Given
        UUID reportId = UUID.randomUUID();
        String endpoint = "/reports/" + reportId + "/full";
        Mockito.doNothing().when(reportService).cascadeDeleteReportByReportId(reportId);

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }
}
