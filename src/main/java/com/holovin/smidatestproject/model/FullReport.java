package com.holovin.smidatestproject.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class FullReport {

    private UUID id;
    private Company company;
    private Timestamp reportDate;
    private BigDecimal totalRevenue;
    private BigDecimal netProfit;
    private String financialData;
    private String comments;
}
