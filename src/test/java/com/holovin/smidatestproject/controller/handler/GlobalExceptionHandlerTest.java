package com.holovin.smidatestproject.controller.handler;

import com.holovin.smidatestproject.exceptions.CompanyNotFoundException;
import com.holovin.smidatestproject.exceptions.UserIsUnauthorizedException;
import com.holovin.smidatestproject.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

//    @Test
//    public void shouldHandleAnotherException() {
//        // Given
//        Exception exception = new Exception("Some error");
//
//        // When
//        ResponseEntity<String> responseEntity = exceptionHandler.handleException(exception);
//
//        // Then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//        assertThat(responseEntity.getBody()).isEqualTo("Internal error");
//    }
}
