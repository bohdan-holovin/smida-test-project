package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.controller.dto.request.FullReportRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportDetailsRequestDto;
import com.holovin.smidatestproject.controller.dto.request.ReportRequestDto;
import com.holovin.smidatestproject.controller.dto.response.FullReportResponseDto;
import com.holovin.smidatestproject.controller.dto.response.ReportDetailsResponseDto;
import com.holovin.smidatestproject.controller.dto.response.ReportResponseDto;
import com.holovin.smidatestproject.model.FullReport;
import com.holovin.smidatestproject.model.Report;
import com.holovin.smidatestproject.model.ReportDetails;
import com.holovin.smidatestproject.service.ReportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.controller.mapper.ReportDtoMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getAllReports() {
        logger.info("Fetching all reports");
        List<ReportResponseDto> reports = toReportResponseDtoList(reportService.getAllReports());
        logger.info("Found {} reports", reports.size());
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getAllReportsByCompanyId(@PathVariable UUID companyId) {
        logger.info("Fetching all reports for company with id {}", companyId);
        List<ReportResponseDto> reports = toReportResponseDtoList(reportService.getAllReportsByCompanyId(companyId));
        logger.info("Found {} reports for company with id {}", reports.size(), companyId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> getReportByReportId(@PathVariable UUID reportId) {
        logger.info("Fetching report with id {}", reportId);
        ReportResponseDto report = toReportResponseDto(reportService.getReportByReportId(reportId));
        logger.info("Found report: {}", report);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{reportId}/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> getReportDetailsByReportId(@PathVariable UUID reportId) {
        logger.info("Fetching details for report with id {}", reportId);
        ReportDetailsResponseDto reportDetails = toReportDetailsResponseDto(reportService.getReportDetailByReportId(reportId));
        logger.info("Found report response: {}", reportDetails);
        return ResponseEntity.ok(reportDetails);
    }

    @GetMapping("/{reportId}/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> getFullReportByReportId(@PathVariable UUID reportId) {
        logger.info("Fetching full report for report with id {}", reportId);
        FullReportResponseDto fullReport = toFullReportResponseDto(reportService.getFullReportByReportId(reportId));
        logger.info("Found full report response: {}", fullReport);
        return ResponseEntity.ok(fullReport);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> createReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        logger.info("Creating report request: {}", reportRequestDto);
        Report report = reportService.createReport(reportRequestDto.getCompanyId(), toReportDetails(reportRequestDto));
        ReportResponseDto reportResponseDto = toReportResponseDto(report);
        logger.info("Created report response: {}", reportResponseDto);
        return ResponseEntity.ok(reportResponseDto);
    }

    @PostMapping("/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> createReportDetails(@Valid @RequestBody ReportDetailsRequestDto reportDetailsRequestDto) {
        logger.info("Creating report details request: {}", reportDetailsRequestDto);
        ReportDetails reportDetails = reportService.createReportDetails(toReportDetails(reportDetailsRequestDto));
        ReportDetailsResponseDto reportDetailsResponseDto = toReportDetailsResponseDto(reportDetails);
        logger.info("Created report details response: {}", reportDetailsResponseDto);
        return ResponseEntity.ok(reportDetailsResponseDto);
    }

    @PostMapping("/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> createFullReport(@Valid @RequestBody FullReportRequestDto fullReportRequestDto) {
        logger.info("Creating full report request: {}", fullReportRequestDto);
        FullReport fullReport = reportService.createFullReport(fullReportRequestDto.getId(), toFullReport(fullReportRequestDto));
        FullReportResponseDto fullReportResponseDto = toFullReportResponseDto(fullReport);
        logger.info("Created full report response: {}", fullReportResponseDto);
        return ResponseEntity.ok(fullReportResponseDto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> updateReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        logger.info("Updating report request: {}", reportRequestDto);
        Report report = reportService.updateReport(reportRequestDto.getCompanyId(), toReportDetails(reportRequestDto));
        ReportResponseDto reportResponseDto = toReportResponseDto(report);
        logger.info("Updated report response: {}", reportResponseDto);
        return ResponseEntity.ok(reportResponseDto);
    }

    @PutMapping("/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> updateReportDetails(@Valid @RequestBody ReportDetailsRequestDto reportDetailsRequestDto) {
        logger.info("Updating report details request: {}", reportDetailsRequestDto);
        ReportDetails reportDetails = reportService.updateReportDetails(toReportDetails(reportDetailsRequestDto));
        ReportDetailsResponseDto reportDetailsResponseDto = toReportDetailsResponseDto(reportDetails);
        logger.info("Updated report details response: {}", reportDetailsResponseDto);
        return ResponseEntity.ok(reportDetailsResponseDto);
    }

    @PutMapping("/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> updateFullReport(@Valid @RequestBody FullReportRequestDto fullReportRequestDto) {
        logger.info("Updating full report request: {}", fullReportRequestDto);
        FullReport fullReport = reportService.updateFullReport(fullReportRequestDto.getCompanyId(), toFullReport(fullReportRequestDto));
        FullReportResponseDto fullReportResponseDto = toFullReportResponseDto(fullReport);
        logger.info("Updated full report response: {}", fullReportResponseDto);
        return ResponseEntity.ok(fullReportResponseDto);
    }

    @DeleteMapping("/{reportId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteReportByReportId(@PathVariable UUID reportId) {
        logger.info("Deleting report with id {}", reportId);
        reportService.deleteReportByReportId(reportId);
        logger.info("Deleted report with id {}", reportId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteReportDetailsByReportId(@PathVariable UUID reportId) {
        logger.info("Deleting report details with id {}", reportId);
        reportService.deleteReportDetailsByReportId(reportId);
        logger.info("Deleted report details with id {}", reportId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteFullReportByReportId(@PathVariable UUID reportId) {
        logger.info("Deleting full report with id {}", reportId);
        reportService.deleteFullReportByReportId(reportId);
        logger.info("Deleted full report with id {}", reportId);
        return ResponseEntity.ok().build();
    }
}
