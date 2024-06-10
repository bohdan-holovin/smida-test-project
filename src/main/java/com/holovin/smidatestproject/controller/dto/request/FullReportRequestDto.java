package com.holovin.smidatestproject.controller.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullReportRequestDto {

    private UUID id;
    private UUID companyId;
    private Timestamp reportDate;
    private BigDecimal totalRevenue;
    private BigDecimal netProfit;
    private String financialData;
    private String comments;
}
