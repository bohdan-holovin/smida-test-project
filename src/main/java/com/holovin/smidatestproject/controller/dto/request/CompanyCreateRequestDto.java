package com.holovin.smidatestproject.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateRequestDto {

    private String name;
    private String registrationNumber;
    private String address;
}
