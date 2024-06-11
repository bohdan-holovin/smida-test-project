package com.holovin.smidatestproject.controller.dto.report.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsRequestDto {

    @NotNull(message = "Report ID cannot be null")
    private UUID reportId;

    @NotNull(message = "Financial data cannot be null")
    @NotEmpty(message = "Financial data cannot be empty")
    @Size(max = 1000, message = "Financial data cannot exceed 1000 characters")
    private String financialData;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;
}
