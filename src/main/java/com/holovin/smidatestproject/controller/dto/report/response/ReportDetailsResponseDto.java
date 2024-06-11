package com.holovin.smidatestproject.controller.dto.report.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class ReportDetailsResponseDto {

    private UUID reportId;
    private String financialData;
    private String comments;
}
