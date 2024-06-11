package com.holovin.smidatestproject.controller.dto.company.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    private UUID id;
    private String name;
    private String registrationNumber;
    private String address;
    private Timestamp createdAt;
}
