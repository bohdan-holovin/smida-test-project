package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserAuthService;
import com.holovin.smidatestproject.service.UserService;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.holovin.smidatestproject.utils.JsonUserMapperUtils.toJsonUserUpdateRequestDto;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnAllUsersForUser() throws Exception {
        // Given
        String endpoint = "/users";
        User user = RandomUtils.createRandomUser();
        when(userService.getAllUsers()).thenReturn(List.of(user));

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(user.getId())))
                .andExpect(jsonPath("$[0].username", equalTo(user.getUsername())))
                .andExpect(jsonPath("$[0].firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$[0].email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$[0].address", equalTo(user.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnUserByIdForUser() throws Exception {
        // Given
        User user = RandomUtils.createRandomUser();
        String endpoint = "/users/id/" + user.getId();
        when(userService.getUserById(user.getId())).thenReturn(user);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(user.getId())))
                .andExpect(jsonPath("$.username", equalTo(user.getUsername())))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.address", equalTo(user.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnUserByUsernameForUser() throws Exception {
        // Given
        User user = RandomUtils.createRandomUser();
        String endpoint = "/users/" + user.getUsername();
        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(user.getId())))
                .andExpect(jsonPath("$.username", equalTo(user.getUsername())))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.address", equalTo(user.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldUpdateUserForUser() throws Exception {
        // Given
        String endpoint = "/users";
        User user = RandomUtils.createRandomUser();
        when(userService.updateUser(any(User.class))).thenReturn(user);

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUserUpdateRequestDto(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(user.getId())))
                .andExpect(jsonPath("$.username", equalTo(user.getUsername())))
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.address", equalTo(user.getAddress())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldDeleteUserForUser() throws Exception {
        // Given
        User user = RandomUtils.createRandomUser();
        String endpoint = "/users/" + user.getId();
        doNothing().when(userService).deleteUserById(user.getId());

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk())
                .andReturn();
    }
}
