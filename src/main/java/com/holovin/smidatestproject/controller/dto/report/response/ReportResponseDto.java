package com.holovin.smidatestproject.controller.dto.report.response;

import com.holovin.smidatestproject.controller.dto.company.response.CompanyResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
public class ReportResponseDto {

    private UUID id;
    private CompanyResponseDto company;
    private Timestamp reportDate;
    private BigDecimal totalRevenue;
    private BigDecimal netProfit;
}
