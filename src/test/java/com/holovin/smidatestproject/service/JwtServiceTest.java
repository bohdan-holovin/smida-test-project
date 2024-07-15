package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest extends AbstractUnitTest {

    @Mock
    private UserDetails userDetails;

    JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    public void shouldGenerateTokenWhenCallGenerateToken() {
        // Given
        String userName = "testUser";

        // When
        String token = jwtService.generateToken(userName);

        // Then
        assertNotNull(token);
        assertThat(token).isNotEmpty();
    }

    @Test
    public void shouldReturnValidUsernameWhenCallExtractUsername() {
        // Given
        String expected = "testUser";
        String token = jwtService.generateToken(expected);

        // When
        String actual = jwtService.extractUsername(token);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldReturnValidExtractExpirationWhenCallExtractExpiration() {
        // Given
        String userName = "testUser";
        String token = jwtService.generateToken(userName);

        // When
        Date expirationDate = jwtService.extractExpiration(token);

        // Then
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    public void shouldReturnTrueWhenTokenIsValid() {
        // Given
        String userName = "testUser";
        String token = jwtService.generateToken(userName);
        when(userDetails.getUsername()).thenReturn(userName);

        // When
        Boolean isValid = jwtService.validateToken(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    public void shouldThrowExpiredJwtExceptionWhenTokenInvalid() {
        // Given
        String userName = "testUser";
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 31))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 30))
                .signWith(jwtService.getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        // When-Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenExpired(token));
    }
}
