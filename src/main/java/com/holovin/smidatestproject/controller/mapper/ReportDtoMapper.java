package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.report.request.GetFullReportRequestDto;
import com.holovin.smidatestproject.controller.dto.report.request.GetReportRequestDto;
import com.holovin.smidatestproject.controller.dto.report.request.GetReportDetailsRequestDto;
import com.holovin.smidatestproject.controller.dto.report.response.FullReportResponseDto;
import com.holovin.smidatestproject.controller.dto.report.response.ReportDetailsResponseDto;
import com.holovin.smidatestproject.controller.dto.report.response.ReportResponseDto;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;

import java.util.List;
import java.util.stream.Collectors;

import static com.holovin.smidatestproject.controller.mapper.CompanyDtoMapper.toCompanyResponseDto;

public class ReportDtoMapper {

    public static Report toReportDetails(GetReportRequestDto getReportRequestDto) {
        Report report = new Report();
        report.setReportDate(getReportRequestDto.getReportDate());
        report.setTotalRevenue(getReportRequestDto.getTotalRevenue());
        report.setNetProfit(getReportRequestDto.getNetProfit());
        return report;
    }

    public static ReportDetails toReportDetails(GetReportDetailsRequestDto getReportDetailsRequestDto) {
        ReportDetails report = new ReportDetails();
        report.setReportId(getReportDetailsRequestDto.getReportId());
        report.setComments(getReportDetailsRequestDto.getComments());
        report.setFinancialData(getReportDetailsRequestDto.getFinancialData());
        return report;
    }

    public static FullReport toFullReport(GetFullReportRequestDto getFullReportRequestDto) {
        FullReport fullReport = new FullReport();
        fullReport.setId(getFullReportRequestDto.getId());
        fullReport.setReportDate(getFullReportRequestDto.getReportDate());
        fullReport.setTotalRevenue(getFullReportRequestDto.getTotalRevenue());
        fullReport.setNetProfit(getFullReportRequestDto.getNetProfit());
        fullReport.setFinancialData(getFullReportRequestDto.getFinancialData());
        fullReport.setComments(getFullReportRequestDto.getComments());
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
