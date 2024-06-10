package com.holovin.smidatestproject.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateRequestDto {

    private UUID id;
    private String name;
    private String registrationNumber;
    private String address;
}
