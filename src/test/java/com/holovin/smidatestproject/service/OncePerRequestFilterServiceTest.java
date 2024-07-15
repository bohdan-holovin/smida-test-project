package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class OncePerRequestFilterServiceTest extends AbstractUnitTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserSecurityService userSecurityService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private OncePerRequestFilterService oncePerRequestFilterService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldMissWhenNoAuthorizationHeader() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        oncePerRequestFilterService.doFilterInternal(request, response, filterChain);

        // Then
        verify(jwtService, never()).extractUsername(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldMissWhenAuthorizationHeaderWithoutBearer() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn("SomeToken 124124231421");

        // When
        oncePerRequestFilterService.doFilterInternal(request, response, filterChain);

        // Then
        verify(jwtService, never()).extractUsername(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldMissWhenInvalidToken() throws ServletException, IOException {
        // Given
        String invalidToken = "Bearer invalidToken";
        when(request.getHeader("Authorization")).thenReturn(invalidToken);
        when(jwtService.extractUsername(anyString())).thenReturn("user");
        when(userSecurityService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtService.validateToken(eq("invalidToken"), any(UserDetails.class))).thenReturn(false);

        // When
        oncePerRequestFilterService.doFilterInternal(request, response, filterChain);

        // Then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        
        verify(jwtService).extractUsername("invalidToken");
        verify(jwtService).validateToken(eq("invalidToken"), any(UserDetails.class));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldReturnAuthTokenWhenValidTokenWithUserAuthentication() throws ServletException, IOException {
        // Given
        String validToken = "Bearer validToken";
        String username = "user";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn(validToken);
        when(jwtService.extractUsername(anyString())).thenReturn(username);
        when(userSecurityService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtService.validateToken(eq("validToken"), eq(userDetails))).thenReturn(true);

        // When
        oncePerRequestFilterService.doFilterInternal(request, response, filterChain);

        // Then
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authToken);

        verify(jwtService).extractUsername("validToken");
        verify(jwtService).validateToken(eq("validToken"), eq(userDetails));
        verify(filterChain).doFilter(request, response);
    }
}
