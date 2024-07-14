package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.holovin.smidatestproject.utils.JsonUserMapperUtils.*;
import static com.holovin.smidatestproject.utils.RandomUtils.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserSecurityController.class)
@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class UserSecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void shouldAccessNotSecuredEndpoint() throws Exception {
        // Given
        String endpoint = "/not-secured";

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isEqualTo("This endpoint is not secure");
    }

    @Test
    void shouldRegisterUser() throws Exception {
        // Given
        String endpoint = "/register";
        User user = createRandomUser();
        when(userSecurityService.registerUser(any(User.class))).thenReturn(user);

        // When-Then
        MvcResult result = mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonRegisterUserRequestDto(user)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("User register successfully with username");
    }

    @Test
    void shouldAuthenticateAndReturnToken() throws Exception {
        // Given
        String endpoint = "/login";
        User user = createRandomUser();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("token");

        // When-Then
        MvcResult result = mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonAuthUserRequestDto(user)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).isEqualTo("token");
    }

    @Test
    void shouldFailAuthentication() throws Exception {
        // Given
        String endpoint = "/login";
        User user = createRandomUser();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonAuthUserRequestDto(user)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void shouldUpdateUserPasswordForAuthorizedRoles() throws Exception {
        // Given
        String endpoint = "/users/";
        User user = createRandomUser();
        when(userSecurityService.updateUserPassword(any())).thenReturn(user);

        // When-Then
        MvcResult result = mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateUserPasswordRequestDto(user)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("username");
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnUpdateUserPassword() throws Exception {
        // Given
        String endpoint = "/users/";
        User user = createRandomUser();

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateUserPasswordRequestDto(user)))
                .andExpect(status().isForbidden());
    }
}
