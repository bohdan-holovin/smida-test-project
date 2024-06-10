package com.holovin.smidatestproject.exceptions;

import java.util.UUID;

public class ReportDetailsNotFoundException extends RuntimeException {
    public ReportDetailsNotFoundException(UUID reportId) {
        super("Report details not found with ID: " + reportId);
    }
}

