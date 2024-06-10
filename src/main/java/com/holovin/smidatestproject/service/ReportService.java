package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exceptions.ReportDetailsNotFoundException;
import com.holovin.smidatestproject.exceptions.ReportNotFoundException;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.repository.ReportDetailsRepository;
import com.holovin.smidatestproject.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.service.mapper.ReportMapper.*;

@Service
@AllArgsConstructor
public class ReportService {

    private CompanyService companyService;
    private ReportRepository reportRepository;
    private ReportDetailsRepository reportDetailsRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getAllReportsByCompanyId(UUID companyId) {
        companyService.getCompanyByCompanyId(companyId);
        return reportRepository.findAllByCompanyId(companyId);
    }

    public Report getReportByReportId(UUID reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));
    }

    public ReportDetails getReportDetailByReportId(UUID reportId) {
        return reportDetailsRepository.findById(reportId)
                .orElseThrow(() -> new ReportDetailsNotFoundException(reportId));
    }

    public FullReport getFullReportByReportId(UUID reportId) {
        Report report = getReportByReportId(reportId);
        ReportDetails reportDetails = getReportDetailByReportId(reportId);
        return toFullReport(report, reportDetails);
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public ReportDetails createReportDetails(ReportDetails report) {
        getReportByReportId(report.getReportId());
        return reportDetailsRepository.save(report);
    }

    public FullReport createFullReport(FullReport fullReport) {
        createReport(toReport(fullReport));
        createReportDetails(toReportDetails(fullReport));
        return fullReport;
    }

    public Report updateReport(Report report) {
        return reportRepository.findById(report.getId())
                .map(it -> {
                    it.setCompany(report.getCompany());
                    it.setNetProfit(report.getNetProfit());
                    it.setReportDate(report.getReportDate());
                    it.setTotalRevenue(report.getTotalRevenue());
                    return it;
                }).orElseThrow(() -> new ReportNotFoundException(report.getId()));
    }

    public ReportDetails updateReportDetails(ReportDetails reportDetails) {
        getReportByReportId(reportDetails.getReportId());
        return reportDetailsRepository.findById(reportDetails.getReportId())
                .map(it -> {
                    it.setComments(reportDetails.getComments());
                    it.setFinancialData(reportDetails.getComments());
                    return it;
                }).orElseThrow(() -> new ReportDetailsNotFoundException(reportDetails.getReportId()));
    }

    public FullReport updateFullreport(FullReport fullReport) {
        Report report = updateReport(toReport(fullReport));
        ReportDetails reportDetails = updateReportDetails(toReportDetails(fullReport));
        return toFullReport(report, reportDetails);
    }

    public void deleteReportByReportId(UUID reportId) {
        getReportByReportId(reportId);
        reportRepository.deleteById(reportId);
    }

    public void deleteReportDetailsByReportId(UUID reportId) {
        getReportByReportId(reportId);
        getReportDetailByReportId(reportId);
        reportDetailsRepository.deleteById(reportId);
    }

    public void deleteFullReportByReportId(UUID reportId) {
        getReportByReportId(reportId);
        getReportDetailByReportId(reportId);
        reportDetailsRepository.deleteById(reportId);
        reportRepository.deleteById(reportId);
    }
}
