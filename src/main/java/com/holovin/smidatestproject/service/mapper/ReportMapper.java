package com.holovin.smidatestproject.service.mapper;

import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;

public class ReportMapper {
    public static FullReport toFullReport(Report report, ReportDetails reportDetails) {
        FullReport fullReport = new FullReport();

        fullReport.setId(report.getId());
        fullReport.setCompany(report.getCompany());
        fullReport.setReportDate(report.getReportDate());
        fullReport.setTotalRevenue(report.getTotalRevenue());
        fullReport.setNetProfit(report.getNetProfit());
        fullReport.setFinancialData(reportDetails.getFinancialData());
        fullReport.setComments(reportDetails.getComments());
        return fullReport;
    }

    public static Report toReport(FullReport fullReport) {
        Report report = new Report();
        report.setId(fullReport.getId());
        report.setCompany(fullReport.getCompany());
        report.setReportDate(fullReport.getReportDate());
        report.setTotalRevenue(fullReport.getTotalRevenue());
        report.setNetProfit(fullReport.getNetProfit());
        return report;
    }

    public static ReportDetails toReportDetails(FullReport fullReport) {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setReportId(fullReport.getId());
        reportDetails.setFinancialData(fullReport.getFinancialData());
        reportDetails.setComments(fullReport.getComments());
        return reportDetails;
    }
}
