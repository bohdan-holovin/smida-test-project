package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.report.request.FullReportRequestDto;
import com.holovin.smidatestproject.controller.dto.report.request.ReportDetailsRequestDto;
import com.holovin.smidatestproject.controller.dto.report.request.ReportRequestDto;
import com.holovin.smidatestproject.controller.dto.report.response.FullReportResponseDto;
import com.holovin.smidatestproject.controller.dto.report.response.ReportDetailsResponseDto;
import com.holovin.smidatestproject.controller.dto.report.response.ReportResponseDto;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ReportDtoMapperTest {

    @Test
    void shouldMapReportRequestDtoToReport() {
        // Given
        Report expectedReport = RandomUtils.createReport(RandomUtils.createCompany());
        ReportRequestDto requestDto = new ReportRequestDto();
        requestDto.setReportDate(expectedReport.getReportDate());
        requestDto.setTotalRevenue(expectedReport.getTotalRevenue());
        requestDto.setNetProfit(expectedReport.getNetProfit());

        // When
        Report actualReport = ReportDtoMapper.toReportDetails(requestDto);

        // Then
        assertThat(actualReport.getReportDate()).isEqualTo(expectedReport.getReportDate());
        assertThat(actualReport.getNetProfit()).isEqualTo(expectedReport.getNetProfit());
        assertThat(actualReport.getTotalRevenue()).isEqualTo(expectedReport.getTotalRevenue());
    }

    @Test
    void shouldMapReportDetailsRequestDtoToReportDetails() {
        // Given
        ReportDetails expectedReportDetails = RandomUtils.createReportDetails();
        ReportDetailsRequestDto requestDto = new ReportDetailsRequestDto();
        requestDto.setReportId(expectedReportDetails.getReportId());
        requestDto.setComments(expectedReportDetails.getComments());
        requestDto.setFinancialData(expectedReportDetails.getFinancialData());

        // When
        ReportDetails actualReportDetails = ReportDtoMapper.toReportDetails(requestDto);

        // Then
        assertThat(actualReportDetails.getReportId()).isEqualTo(expectedReportDetails.getReportId());
        assertThat(actualReportDetails.getComments()).isEqualTo(expectedReportDetails.getComments());
        assertThat(actualReportDetails.getFinancialData()).isEqualTo(expectedReportDetails.getFinancialData());
    }

    @Test
    void shouldMapFullReportRequestDtoToFullReport() {
        // Given
        FullReport expectedFullReport = RandomUtils.createFullReport();
        FullReportRequestDto requestDto = new FullReportRequestDto();
        requestDto.setId(expectedFullReport.getId());
        requestDto.setReportDate(expectedFullReport.getReportDate());
        requestDto.setTotalRevenue(expectedFullReport.getTotalRevenue());
        requestDto.setNetProfit(expectedFullReport.getNetProfit());
        requestDto.setFinancialData(expectedFullReport.getFinancialData());
        requestDto.setComments(expectedFullReport.getComments());

        // When
        FullReport actualFullReport = ReportDtoMapper.toFullReport(requestDto);

        // Then
        assertThat(actualFullReport.getId()).isEqualTo(expectedFullReport.getId());
        assertThat(actualFullReport.getReportDate()).isEqualTo(expectedFullReport.getReportDate());
        assertThat(actualFullReport.getTotalRevenue()).isEqualTo(expectedFullReport.getTotalRevenue());
        assertThat(actualFullReport.getNetProfit()).isEqualTo(expectedFullReport.getNetProfit());
        assertThat(actualFullReport.getFinancialData()).isEqualTo(expectedFullReport.getFinancialData());
        assertThat(actualFullReport.getComments()).isEqualTo(expectedFullReport.getComments());
    }

    @Test
    void shouldMapReportToReportResponseDto() {
        // Given
        Company testCompany = RandomUtils.createCompany();
        Report testReport = RandomUtils.createReport(testCompany);
        ReportResponseDto expectedDto = new ReportResponseDto();
        expectedDto.setId(testReport.getId());
        expectedDto.setCompany(CompanyDtoMapper.toCompanyResponseDto(testCompany));
        expectedDto.setReportDate(testReport.getReportDate());
        expectedDto.setTotalRevenue(testReport.getTotalRevenue());
        expectedDto.setNetProfit(testReport.getNetProfit());

        // When
        ReportResponseDto actualDto = ReportDtoMapper.toReportResponseDto(testReport);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void shouldMapReportListToReportResponseDtoList() {
        // Given
        Company testCompany = RandomUtils.createCompany();
        List<Report> testReports = List.of(RandomUtils.createReport(testCompany), RandomUtils.createReport(testCompany));
        List<ReportResponseDto> expected = testReports.stream()
                .map(report -> {
                    ReportResponseDto dto = new ReportResponseDto();
                    dto.setId(report.getId());
                    dto.setCompany(CompanyDtoMapper.toCompanyResponseDto(testCompany));
                    dto.setReportDate(report.getReportDate());
                    dto.setTotalRevenue(report.getTotalRevenue());
                    dto.setNetProfit(report.getNetProfit());
                    return dto;
                })
                .collect(Collectors.toList());

        // When
        List<ReportResponseDto> actual = ReportDtoMapper.toReportResponseDtoList(testReports);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldMapReportDetailsToReportDetailsResponseDto() {
        // Given
        ReportDetails testReportDetails = RandomUtils.createReportDetails();
        ReportDetailsResponseDto expectedDto = new ReportDetailsResponseDto();
        expectedDto.setReportId(testReportDetails.getReportId());
        expectedDto.setFinancialData(testReportDetails.getFinancialData());
        expectedDto.setComments(testReportDetails.getComments());

        // When
        ReportDetailsResponseDto actualDto = ReportDtoMapper.toReportDetailsResponseDto(testReportDetails);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void shouldMapFullReportToFullReportResponseDto() {
        // Given
        Company testCompany = RandomUtils.createCompany();
        FullReport testFullReport = RandomUtils.createFullReport();
        testFullReport.setCompany(testCompany);

        FullReportResponseDto expectedDto = new FullReportResponseDto();
        expectedDto.setId(testFullReport.getId());
        expectedDto.setCompany(CompanyDtoMapper.toCompanyResponseDto(testCompany));
        expectedDto.setReportDate(testFullReport.getReportDate());
        expectedDto.setTotalRevenue(testFullReport.getTotalRevenue());
        expectedDto.setNetProfit(testFullReport.getNetProfit());
        expectedDto.setFinancialData(testFullReport.getFinancialData());
        expectedDto.setComments(testFullReport.getComments());

        // When
        FullReportResponseDto actualDto = ReportDtoMapper.toFullReportResponseDto(testFullReport);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }
}
