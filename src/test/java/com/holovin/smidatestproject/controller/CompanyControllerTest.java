package com.holovin.smidatestproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.AbstractIntegratedTest;
import com.holovin.smidatestproject.controller.dto.company.response.CompanyResponseDto;
import com.holovin.smidatestproject.controller.mapper.CompanyDtoMapper;
import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.service.CompanyDeleteFacadeService;
import com.holovin.smidatestproject.service.CompanyService;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static com.holovin.smidatestproject.utils.JsonCompanyMapperUtils.toJsonCreateCompanyRequestDto;
import static com.holovin.smidatestproject.utils.JsonCompanyMapperUtils.toJsonUpdateCompanyRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
public class CompanyControllerTest extends AbstractIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private CompanyDeleteFacadeService companyDeleteFacadeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldReturnAllCompaniesForAdmin() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        List<Company> companyList = List.of(company);
        CompanyResponseDto expectedDto = CompanyDtoMapper.toCompanyResponseDto(company);
        when(companyService.getAllCompanies()).thenReturn(companyList);

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CompanyResponseDto[] actualDtoList = objectMapper.readValue(json, CompanyResponseDto[].class);
        assertThat(actualDtoList.length).isEqualTo(1);
        assertThat(actualDtoList[0]).isEqualTo(expectedDto);
    }

    @Test
    @WithMockUser(authorities = {"USER", "ACCOUNTANT", "COMPANY_OWNER", "ADMIN"})
    void shouldReturnCompanyByIdForAuthorizedRoles() throws Exception {
        // Given
        UUID companyId = UUID.randomUUID();
        String endpoint = "/companies/" + companyId;
        Company company = RandomUtils.createRandomCompany();
        CompanyResponseDto expectedDto = CompanyDtoMapper.toCompanyResponseDto(company);
        when(companyService.getCompanyByCompanyId(companyId)).thenReturn(company);

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CompanyResponseDto actualDto = objectMapper.readValue(json, CompanyResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @WithMockUser(authorities = {"USER", "ACCOUNTANT", "COMPANY_OWNER", "ADMIN"})
    void shouldCreateCompanyForAuthorizedRoles() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        CompanyResponseDto expectedDto = CompanyDtoMapper.toCompanyResponseDto(company);
        when(companyService.createUpdate(any(Company.class))).thenReturn(company);

        // When-Then
        MvcResult result = mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCreateCompanyRequestDto(company)))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CompanyResponseDto actualDto = objectMapper.readValue(json, CompanyResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @WithMockUser(authorities = {"COMPANY_OWNER", "ADMIN"})
    void shouldUpdateCompanyForAuthorizedRoles() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();
        CompanyResponseDto expectedDto = CompanyDtoMapper.toCompanyResponseDto(company);
        when(companyService.updateCompany(any(Company.class))).thenReturn(company);

        // When-Then
        MvcResult result = mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateCompanyRequestDto(company)))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CompanyResponseDto actualDto = objectMapper.readValue(json, CompanyResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @WithMockUser(authorities = {"COMPANY_OWNER", "ADMIN"})
    void shouldDeleteCompanyForAuthorizedRoles() throws Exception {
        // Given
        UUID companyId = UUID.randomUUID();
        String endpoint = "/companies/" + companyId;

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnGetAllCompanies() throws Exception {
        // Given
        String endpoint = "/companies";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnGetCompanyById() throws Exception {
        // Given
        UUID companyId = UUID.randomUUID();
        String endpoint = "/companies/" + companyId;

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnCreateCompany() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonCreateCompanyRequestDto(company)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnUpdateCompany() throws Exception {
        // Given
        String endpoint = "/companies";
        Company company = RandomUtils.createRandomCompany();

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateCompanyRequestDto(company)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnDeleteCompany() throws Exception {
        // Given
        UUID companyId = UUID.randomUUID();
        String endpoint = "/companies/" + companyId;

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isForbidden());
    }
}
