package com.holovin.smidatestproject.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullReportRequestDto {

    @NotNull(message = "ID cannot be null")
    private UUID id;

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

    @NotNull(message = "Financial data cannot be null")
    @NotEmpty(message = "Financial data cannot be empty")
    @Size(max = 1000, message = "Financial data cannot exceed 1000 characters")
    private String financialData;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;
}
