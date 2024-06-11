package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyDeleteService;
import com.holovin.smidatestproject.service.CompanyService;
import com.holovin.smidatestproject.service.UserDetailsServiceImpl;
import com.holovin.smidatestproject.utils.RandomUtils;
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

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.utils.JsonCompanyMapperUtils.toJsonCompanyCreateDto;
import static com.holovin.smidatestproject.utils.JsonCompanyMapperUtils.toJsonCompanyUpdateDto;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private CompanyDeleteService companyDeleteService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnAllCompaniesForUser() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        List<Company> companyList = List.of(company);
        when(companyService.getAllCompanies()).thenReturn(companyList);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(company.getId().toString())))
                .andExpect(jsonPath("$[0].name", equalTo(company.getName())))
                .andExpect(jsonPath("$[0].registrationNumber", equalTo(company.getRegistrationNumber())))
                .andExpect(jsonPath("$[0].address", equalTo(company.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnCompanyByIdForUser() throws Exception {
        // Given
        Company company = RandomUtils.createRandomCompany();
        String endpoint = "/companies/" + company.getId();

        when(companyService.getCompanyByCompanyId(company.getId())).thenReturn(company);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(company.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(company.getName())))
                .andExpect(jsonPath("$.registrationNumber", equalTo(company.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", equalTo(company.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldCreateCompanyForAdmin() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        when(companyService.createUpdate(any(Company.class))).thenReturn(company);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCompanyCreateDto(company)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(company.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(company.getName())))
                .andExpect(jsonPath("$.registrationNumber", equalTo(company.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", equalTo(company.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnForbiddenForUserTryingToCreateCompany() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCompanyCreateDto(company)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldUpdateCompanyForAdmin() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        when(companyService.updateCompany(any(Company.class))).thenReturn(company);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCompanyUpdateDto(company)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(company.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(company.getName())))
                .andExpect(jsonPath("$.registrationNumber", equalTo(company.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", equalTo(company.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnForbiddenForUserTryingToUpdateCompany() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCompanyUpdateDto(company)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldDeleteCompanyForAdmin() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        String endpoint = "/companies/" + id;
        Mockito.doNothing().when(companyDeleteService).cascadeCompanyDelete(id);
        when(companyService.getCompanyByCompanyId(id)).thenReturn(new Company());

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnForbiddenForUserTryingToDeleteCompany() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        String endpoint = "/companies/" + id;
        Mockito.doNothing().when(companyDeleteService).cascadeCompanyDelete(id);

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
