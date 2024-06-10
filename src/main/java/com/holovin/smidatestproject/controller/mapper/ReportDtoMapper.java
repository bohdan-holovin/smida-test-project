package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.FullReportRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportDetailsRequestDto;
import com.holovin.smidatestproject.controller.dto.response.FullReportResponseDto;
import com.holovin.smidatestproject.controller.dto.response.ReportDetailsResponseDto;
import com.holovin.smidatestproject.controller.dto.response.ReportResponseDto;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;

import java.util.List;
import java.util.stream.Collectors;

import static com.holovin.smidatestproject.controller.mapper.CompanyDtoMapper.toCompanyResponseDto;

public class ReportDtoMapper {

    public static Report toReportDetails(ReportRequestDto reportRequestDto) {
        Report report = new Report();
        report.setReportDate(reportRequestDto.getReportDate());
        report.setTotalRevenue(reportRequestDto.getTotalRevenue());
        report.setNetProfit(reportRequestDto.getNetProfit());
        return report;
    }

    public static ReportDetails toReportDetails(ReportDetailsRequestDto reportDetailsRequestDto) {
        ReportDetails report = new ReportDetails();
        report.setReportId(reportDetailsRequestDto.getReportId());
        report.setComments(reportDetailsRequestDto.getComments());
        report.setFinancialData(reportDetailsRequestDto.getFinancialData());
        return report;
    }

    public static FullReport toFullReport(FullReportRequestDto fullReportRequestDto) {
        FullReport fullReport = new FullReport();
        fullReport.setId(fullReportRequestDto.getId());
        fullReport.setReportDate(fullReportRequestDto.getReportDate());
        fullReport.setTotalRevenue(fullReportRequestDto.getTotalRevenue());
        fullReport.setNetProfit(fullReportRequestDto.getNetProfit());
        fullReport.setFinancialData(fullReportRequestDto.getFinancialData());
        fullReport.setComments(fullReportRequestDto.getComments());
        return fullReport;
    }

    public static ReportResponseDto toReportResponseDto(Report report) {
        ReportResponseDto dto = new ReportResponseDto();
        dto.setId(report.getId());
        dto.setCompany(toCompanyResponseDto(report.getCompany()));
        dto.setReportDate(report.getReportDate());
        dto.setTotalRevenue(report.getTotalRevenue());
        dto.setNetProfit(report.getNetProfit());
        return dto;
    }

    public static List<ReportResponseDto> toReportResponseDtoList(List<Report> reports) {
        return reports.stream()
                .map(ReportDtoMapper::toReportResponseDto)
                .collect(Collectors.toList());
    }

    public static ReportDetailsResponseDto toReportDetailsResponseDto(ReportDetails reportDetails) {
        ReportDetailsResponseDto dto = new ReportDetailsResponseDto();
        dto.setReportId(reportDetails.getReportId());
        dto.setFinancialData(reportDetails.getFinancialData());
        dto.setComments(reportDetails.getComments());
        return dto;
    }

    public static FullReportResponseDto toFullReportResponseDto(FullReport fullReport) {
        FullReportResponseDto dto = new FullReportResponseDto();
        dto.setId(fullReport.getId());
        dto.setCompany(toCompanyResponseDto(fullReport.getCompany()));
        dto.setReportDate(fullReport.getReportDate());
        dto.setTotalRevenue(fullReport.getTotalRevenue());
        dto.setNetProfit(fullReport.getNetProfit());
        dto.setFinancialData(fullReport.getFinancialData());
        dto.setComments(fullReport.getComments());
        return dto;
    }
}
