package com.holovin.smidatestproject.controller.handler;

import com.holovin.smidatestproject.exceptions.CompanyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleCompanyNotFoundException() {
        UUID companyId = UUID.randomUUID();
        CompanyNotFoundException exception = new CompanyNotFoundException(companyId);

        ResponseEntity<String> responseEntity = globalExceptionHandler.handleCompanyNotFoundException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo("Company not found with ID: " + companyId);
    }
}
