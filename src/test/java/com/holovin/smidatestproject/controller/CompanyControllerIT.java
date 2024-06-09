package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyService;
import com.holovin.smidatestproject.utils.RandomCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = RandomCompany.create();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllCompanies() throws Exception {
        // Given
        List<Company> expectedCompanies = List.of(testCompany);
        given(companyService.findAll()).willReturn(expectedCompanies);

        // When-Then
        mockMvc.perform(get("/companies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testCompany.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(testCompany.getName())))
                .andExpect(jsonPath("$[0].registrationNumber", is(testCompany.getRegistrationNumber())))
                .andExpect(jsonPath("$[0].address", is(testCompany.getAddress())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCompanyById() throws Exception {
        // Given
        UUID id = testCompany.getId();
        given(companyService.findById(id)).willReturn(testCompany);

        // When-Then
        mockMvc.perform(get("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testCompany.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCompany.getName())))
                .andExpect(jsonPath("$.registrationNumber", is(testCompany.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", is(testCompany.getAddress())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCompany() throws Exception {
        // Given
        given(companyService.create(any(Company.class))).willReturn(testCompany);

        // When-Then
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"" + testCompany.getName() + "\", " +
                                "\"registrationNumber\": \"" + testCompany.getRegistrationNumber() + "\", " +
                                "\"address\": \"" + testCompany.getAddress() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testCompany.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCompany.getName())))
                .andExpect(jsonPath("$.registrationNumber", is(testCompany.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", is(testCompany.getAddress())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCompany() throws Exception {
        // Given
        UUID id = testCompany.getId();
        Company updatedCompany = RandomCompany.create();
        given(companyService.updateCompany(any(UUID.class), any(Company.class))).willReturn(updatedCompany);

        // When-Then
        mockMvc.perform(put("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"" + updatedCompany.getName() + "\", " +
                                "\"registrationNumber\": \"" + updatedCompany.getRegistrationNumber() + "\", " +
                                "\"address\": \"" + updatedCompany.getAddress() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedCompany.getId().toString())))
                .andExpect(jsonPath("$.name", is(updatedCompany.getName())))
                .andExpect(jsonPath("$.registrationNumber", is(updatedCompany.getRegistrationNumber())))
                .andExpect(jsonPath("$.address", is(updatedCompany.getAddress())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCompany() throws Exception {
        // Given
        UUID id = testCompany.getId();
        doNothing().when(companyService).deleteById(id);

        // When & Then
        mockMvc.perform(delete("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
