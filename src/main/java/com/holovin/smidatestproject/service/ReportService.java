package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exceptions.CompanyNotFoundException;
import com.holovin.smidatestproject.exceptions.ReportDetailsNotFoundException;
import com.holovin.smidatestproject.exceptions.ReportNotFoundException;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.repository.ReportDetailsRepository;
import com.holovin.smidatestproject.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return Optional.of(companyService.getCompanyByCompanyId(companyId))
                .map(it -> reportRepository.findAllByCompanyId(companyId))
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public Report getReportByReportId(UUID reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));
    }

    public ReportDetails getReportDetailByReportId(UUID reportId) {
        return reportDetailsRepository.findById(reportId)
                .orElseThrow(() -> new ReportDetailsNotFoundException(reportId));
    }

    public FullReport getFullReportByReportId(UUID reportId) {
        return Optional.of(getReportByReportId(reportId))
                .map(report -> {
                    ReportDetails reportDetails = getReportDetailByReportId(reportId);
                    return toFullReport(report, reportDetails);
                })
                .orElseThrow(() -> new ReportNotFoundException(reportId));
    }

    public Report createReport(UUID companyId, Report report) {
        return Optional.of(companyService.getCompanyByCompanyId(companyId))
                .map(it -> {
                    report.setCompany(it);
                    return report;
                })
                .map(reportRepository::save)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    public ReportDetails createReportDetails(ReportDetails report) {
        return Optional.of(getReportByReportId(report.getReportId()))
                .map(it -> reportDetailsRepository.save(report))
                .orElseThrow(() -> new ReportNotFoundException(report.getReportId()));
    }

    public FullReport createFullReport(UUID companyId, FullReport fullReport) {
        Report report = createReport(companyId, toReport(fullReport));
        ReportDetails reportDetails = createReportDetails(toReportDetails(fullReport));
        return toFullReport(report, reportDetails);
    }

    public Report updateReport(UUID companyId, Report report) {
        Company companyByCompanyId = companyService.getCompanyByCompanyId(companyId);
        return reportRepository.findById(report.getId())
                .map(it -> {
                    it.setCompany(companyByCompanyId);
                    it.setNetProfit(report.getNetProfit());
                    it.setReportDate(report.getReportDate());
                    it.setTotalRevenue(report.getTotalRevenue());
                    return it;
                })
                .map(reportRepository::save)
                .orElseThrow(() -> new ReportNotFoundException(report.getId()));
    }

    public ReportDetails updateReportDetails(ReportDetails reportDetails) {
        getReportByReportId(reportDetails.getReportId());
        return reportDetailsRepository.findById(reportDetails.getReportId())
                .map(it -> {
                    it.setComments(reportDetails.getComments());
                    it.setFinancialData(reportDetails.getComments());
                    return it;
                })
                .map(reportDetailsRepository::save)
                .orElseThrow(() -> new ReportDetailsNotFoundException(reportDetails.getReportId()));
    }

    public FullReport updateFullReport(UUID companyId, FullReport fullReport) {
        Report report = updateReport(companyId, toReport(fullReport));
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
