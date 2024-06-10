package com.holovin.smidatestproject.controller.dto.request;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetailsRequestDto {

    private UUID reportId;
    private String financialData;
    private String comments;
}
