package com.holovin.smidatestproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.controller.dto.request.FullReportRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportDetailsRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportRequestDto;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;

public class JsonReportMapperUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonReportRequestDto(Report report) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new ReportRequestDto(
                        report.getCompany().getId(),
                        report.getReportDate(),
                        report.getTotalRevenue(),
                        report.getNetProfit()
                )
        );
    }

    public static String toJsonReportDetailsRequestDto(ReportDetails reportDetails) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new ReportDetailsRequestDto(
                        reportDetails.getReportId(),
                        reportDetails.getFinancialData(),
                        reportDetails.getComments()
                )
        );
    }

    public static String toJsonFullReportRequestDto(FullReport fullReport) throws JsonProcessingException {

        return objectMapper.writeValueAsString(
                new FullReportRequestDto(
                        fullReport.getId(),
                        fullReport.getCompany().getId(),
                        fullReport.getReportDate(),
                        fullReport.getTotalRevenue(),
                        fullReport.getNetProfit(),
                        fullReport.getFinancialData(),
                        fullReport.getComments()
                )
        );
    }
}
