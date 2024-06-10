package com.holovin.smidatestproject.controller.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
public class FullReportResponseDto {

    private UUID id;
    private CompanyResponseDto company;
    private Timestamp reportDate;
    private BigDecimal totalRevenue;
    private BigDecimal netProfit;
    private String financialData;
    private String comments;
}
