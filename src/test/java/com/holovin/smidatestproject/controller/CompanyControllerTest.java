//package com.holovin.smidatestproject.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.holovin.smidatestproject.config.jwt.JwtAuthFilter;
//import com.holovin.smidatestproject.model.Company;
//import com.holovin.smidatestproject.service.CompanyService;
//import com.holovin.smidatestproject.utils.RandomUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CompanyController.class)
//public class CompanyControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CompanyService companyService;
//
//    @MockBean
//    private JwtAuthFilter jwtAuthFilter;
//
//    private ObjectMapper objectMapper;
//    private Company testCompany;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        testCompany = RandomUtils.createCompany();
//    }
//
//    @Test
//    @WithMockUser(authorities = {"USER"})
//    void getAllCompanies_asUser_shouldReturnCompanies() throws Exception {
//        List<Company> companies = Collections.singletonList(testCompany);
//        Mockito.when(companyService.findAll()).thenReturn(companies);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name").value("Test Company"));
//    }
//
////    @Test
////    @WithMockUser(authorities = {"USER"})
////    void getCompanyById_asUser_shouldReturnCompany() throws Exception {
////        UUID companyId = testCompany.getId();
////        Mockito.when(companyService.findById(companyId)).thenReturn(testCompany);
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", companyId))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.name").value("Test Company"));
////    }
////
////    @Test
////    @WithMockUser(authorities = {"ADMIN"})
////    void createCompany_asAdmin_shouldCreateCompany() throws Exception {
////        Mockito.when(companyService.create(any(Company.class))).thenReturn(testCompany);
////
////        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(testCompany)))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.name").value("New Company"));
////    }
////
////    @Test
////    @WithMockUser(authorities = {"ADMIN"})
////    void updateCompany_asAdmin_shouldUpdateCompany() throws Exception {
////        UUID companyId = testCompany.getId();
////        Mockito.when(companyService.updateCompany(any(UUID.class), any(Company.class))).thenReturn(testCompany);
////
////        mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", companyId)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(testCompany)))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.name").value("Updated Company"));
////    }
//
////    @Test
////    @WithMockUser(authorities = {"ADMIN"})
////    void deleteCompany_asAdmin_shouldDeleteCompany() throws Exception {
////        UUID companyId = UUID.randomUUID();
////        Mockito.doNothing().when(companyService).deleteById(companyId);
////        Mockito.when(companyService.findById(companyId)).thenReturn(new Company(companyId, "To be deleted"));
////
////        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", companyId))
////                .andExpect(status().isOk());
////    }
////
////    @Test
////    @WithMockUser(authorities = {"USER"})
////    void createCompany_asUser_shouldReturnForbidden() throws Exception {
////        Company company = new Company(UUID.randomUUID(), "New Company");
////
////        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(company)))
////                .andExpect(status().isForbidden());
////    }
//}
