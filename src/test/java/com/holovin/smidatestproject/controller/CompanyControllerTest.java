package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyService;
import com.holovin.smidatestproject.service.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static com.holovin.smidatestproject.utils.RandomUtils.createCompany;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnAllCompaniesForUser() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = createCompany();
        when(companyService.findAll()).thenReturn(Collections.singletonList(company));

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"" + company.getId() + "\"}]"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnCompanyByIdForUser() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        String endpoint = "/companies/" + id;
        Company company = new Company();
        company.setId(id);
        when(companyService.findById(id)).thenReturn(company);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + id + "\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldCreateCompanyForAdmin() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = new Company();
        company.setId(UUID.randomUUID());
        when(companyService.create(any(Company.class))).thenReturn(company);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Company\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + company.getId() + "\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldUpdateCompanyForAdmin() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = new Company();
        company.setId(UUID.randomUUID());
        when(companyService.updateCompany(any(Company.class))).thenReturn(company);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + company.getId() + "\", \"name\":\"Updated Company\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + company.getId() + "\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldDeleteCompanyForAdmin() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        String endpoint = "/companies/" + id;
        Mockito.doNothing().when(companyService).deleteById(id);
        when(companyService.findById(id)).thenReturn(new Company());

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnForbiddenForUserTryingToCreateCompany() throws Exception {
        // Given
        String endpoint = "/companies";

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Company\"}"))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
