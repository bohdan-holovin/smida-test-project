//package com.holovin.smidatestproject.service;
//
//import com.holovin.smidatestproject.exceptions.ResourceNotFoundException;
//import com.holovin.smidatestproject.model.Report;
//import com.holovin.smidatestproject.repository.ReportRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class ReportService {
//
//    private CompanyService companyService;
//    private ReportRepository reportRepository;
//
//    public List<Report> getAllReports() {
//        return reportRepository.findAll();
//    }
//
//    public Optional<Report> getReportById(UUID reportId) {
//        return reportRepository.findById(reportId);
//    }
//
//    public Optional<Report> getDetailReportById(UUID reportId) {
//        Optional<Report> reportById = reportRepository.findById(reportId);
//        return reportById;
//    }
//
//    public List<Report> getReportsByCompany(UUID companyId) {
//        return reportRepository.findAllByCompanyId(companyId);
//    }
//
//    public List<Report> getDetailReportsByCompany(UUID companyId) {
//        return reportRepository.findAllByCompanyId(companyId);
//    }
//
//    public Report createReport(Report report) {
//        return reportRepository.save(report);
//    }
//
//    public Report updateReport(UUID id, Report reportDetails) {
//        return reportRepository.findById(id).map(report -> {
//            report.setCompany(reportDetails.getCompany());
//            report.setReportDate(reportDetails.getReportDate());
//            report.setTotalRevenue(reportDetails.getTotalRevenue());
//            report.setNetProfit(reportDetails.getNetProfit());
//            return reportRepository.save(report);
//        }).orElseThrow(() -> new ResourceNotFoundException("Report not found with id " + id));
//    }
//
//    public void deleteReport(UUID id) {
//        reportRepository.deleteById(id);
//    }
//}
