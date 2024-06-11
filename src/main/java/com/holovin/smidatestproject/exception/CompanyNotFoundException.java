package com.holovin.smidatestproject.exception;

import java.util.UUID;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(UUID companyId) {
        super("Company not found with ID: " + companyId);
    }
}
