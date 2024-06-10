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
import lombok.AllArgsConstructor;
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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getAllReports() {
        return ResponseEntity.ok(toReportResponseDtoList(reportService.getAllReports()));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getAllReportsByCompanyId(@PathVariable UUID companyId) {
        return ResponseEntity.ok(toReportResponseDtoList(reportService.getAllReportsByCompanyId(companyId)));
    }

    @GetMapping("/{reportId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> getReportByReportId(@PathVariable UUID reportId) {
        return ResponseEntity.ok(toReportResponseDto(reportService.getReportByReportId(reportId)));
    }

    @GetMapping("/{reportId}/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> getReportDetailsByReportId(@PathVariable UUID reportId) {
        return ResponseEntity.ok(toReportDetailsResponseDto(reportService.getReportDetailByReportId(reportId)));
    }

    @GetMapping("/{reportId}/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> getFullReportByReportId(@PathVariable UUID reportId) {
        return ResponseEntity.ok(toFullReportResponseDto(reportService.getFullReportByReportId(reportId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> createReport(@RequestBody ReportRequestDto reportRequestDto) {
        Report report = reportService.createReport(
                reportRequestDto.getCompanyId(), toReportDetails(reportRequestDto)
        );
        return ResponseEntity.ok(toReportResponseDto(report));
    }

    @PostMapping("/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> createReportDetails(
            @RequestBody ReportDetailsRequestDto reportDetailsRequestDto) {
        ReportDetails reportDetails = reportService.createReportDetails(toReportDetails(reportDetailsRequestDto));
        return ResponseEntity.ok(toReportDetailsResponseDto(reportDetails));
    }

    @PostMapping("/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> createFullReport(@RequestBody FullReportRequestDto fullReportRequestDto) {
        FullReport fullReport = reportService.createFullReport(fullReportRequestDto.getId(), toFullReport(fullReportRequestDto));
        return ResponseEntity.ok(toFullReportResponseDto(fullReport));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportResponseDto> updateReport(@RequestBody ReportRequestDto reportRequestDto) {
        Report report = reportService.updateReport(
                reportRequestDto.getCompanyId(), toReportDetails(reportRequestDto)
        );
        return ResponseEntity.ok(toReportResponseDto(report));
    }

    @PutMapping("/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ReportDetailsResponseDto> updateReportDetails(@RequestBody ReportDetailsRequestDto reportDetailsRequestDto) {
        ReportDetails reportDetails = reportService.updateReportDetails(toReportDetails(reportDetailsRequestDto));
        return ResponseEntity.ok(toReportDetailsResponseDto(reportDetails));
    }

    @PutMapping("/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<FullReportResponseDto> updateFullReport(@RequestBody FullReportRequestDto fullReportRequestDto) {
        FullReport fullReport = reportService.updateFullReport(fullReportRequestDto.getCompanyId(), toFullReport(fullReportRequestDto));
        return ResponseEntity.ok(toFullReportResponseDto(fullReport));
    }

    @DeleteMapping("/{reportId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteReportByReportId(@PathVariable UUID reportId) {
        reportService.deleteReportByReportId(reportId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}/details")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteReportDetailsByReportId(@PathVariable UUID reportId) {
        reportService.deleteReportDetailsByReportId(reportId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reportId}/full")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteFullReportByReportId(@PathVariable UUID reportId) {
        reportService.deleteFullReportByReportId(reportId);
        return ResponseEntity.ok().build();
    }
}
