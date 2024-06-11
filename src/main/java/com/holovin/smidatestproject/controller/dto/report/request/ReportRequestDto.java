package com.holovin.smidatestproject.controller.dto.report.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {

    @NotNull(message = "Company ID cannot be null")
    private UUID companyId;

    @NotNull(message = "Report date cannot be null")
    private Timestamp reportDate;

    @NotNull(message = "Total revenue cannot be null")
    @Positive(message = "Total revenue must be positive")
    private BigDecimal totalRevenue;

    @NotNull(message = "Net profit cannot be null")
    @Positive(message = "Net profit must be positive")
    private BigDecimal netProfit;
}
