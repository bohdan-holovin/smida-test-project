package com.holovin.smidatestproject.controller.handler;

import com.holovin.smidatestproject.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleCompanyNotFoundException() {
        // Given
        UUID companyId = UUID.randomUUID();
        CompanyNotFoundException exception = new CompanyNotFoundException(companyId);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleCompanyNotFoundException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleUserNotFoundException() {
        // Given
        String username = "username";
        UserNotFoundException exception = new UserNotFoundException(username);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleUsernameNotFoundException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleReportNotFoundException() {
        // Given
        UUID reportId = UUID.randomUUID();
        ReportNotFoundException exception = new ReportNotFoundException(reportId);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleReportNotFoundException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleReportDetailsNotFoundException() {
        // Given
        UUID reportId = UUID.randomUUID();
        ReportDetailsNotFoundException exception = new ReportDetailsNotFoundException(reportId);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleReportDetailsNotFoundException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleUserIsUnauthorizedException() {
        // Given
        String username = "username";
        UserIsUnauthorizedException exception = new UserIsUnauthorizedException(username);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleUserIsUnauthorizedException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleExpiredJwtException() {
        // Given
        ExpiredJwtException exception = mock(ExpiredJwtException.class);

        // When
        ResponseEntity<String> responseEntity = exceptionHandler.handleExpiredJwtException(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(responseEntity.getBody()).isEqualTo(exception.getMessage());
    }

    @Test
    public void shouldHandleMethodArgumentNotValidException() {
        // Given
        BindException bindException = new BindException(new Object(), "target");
        bindException.addError(new FieldError("target", "field1", "Field1 error message"));
        bindException.addError(new FieldError("target", "field2", "Field2 error message"));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(mock(MethodParameter.class), bindException);

        // When
        ResponseEntity<Map<String, String>> responseEntity = exceptionHandler.handleValidationExceptions(exception);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).containsEntry("field1", "Field1 error message");
        assertThat(responseEntity.getBody()).containsEntry("field2", "Field2 error message");
    }
}
