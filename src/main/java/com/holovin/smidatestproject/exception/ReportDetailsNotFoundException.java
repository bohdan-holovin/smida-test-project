package com.holovin.smidatestproject.exception;

import java.util.UUID;

public class ReportDetailsNotFoundException extends RuntimeException {
    public ReportDetailsNotFoundException(UUID reportId) {
        super("Report details not found with ID: " + reportId);
    }
}

