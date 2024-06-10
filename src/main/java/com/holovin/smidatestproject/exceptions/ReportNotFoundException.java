package com.holovin.smidatestproject.exceptions;

import java.util.UUID;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(UUID reportId) {
        super("Report not found with ID: " + reportId);
    }
}

