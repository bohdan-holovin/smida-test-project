package com.holovin.smidatestproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holovin.smidatestproject.AbstractIntegratedTest;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.controller.mapper.UserDtoMapper;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserService;
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

import static com.holovin.smidatestproject.utils.JsonUserMapperUtils.toJsonUpdateUserPersonalDataRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldReturnAllUsersForAdmin() throws Exception {
        // Given
        String endpoint = "/users";
        User user = RandomUtils.createRandomUser();
        List<User> userList = List.of(user);
        UserResponseDto expectedDto = UserDtoMapper.toUserResponseDto(user);
        when(userService.getAllUsers()).thenReturn(userList);

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserResponseDto[] actualDtoList = objectMapper.readValue(json, UserResponseDto[].class);
        assertThat(actualDtoList.length).isEqualTo(1);
        assertThat(actualDtoList[0]).isEqualTo(expectedDto);

        verify(userService).getAllUsers();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldReturnUserByIdForAdmin() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        String endpoint = "/users/id/" + userId;
        User user = RandomUtils.createRandomUser();
        UserResponseDto expectedDto = UserDtoMapper.toUserResponseDto(user);
        when(userService.getUserById(userId)).thenReturn(user);

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserResponseDto actualDto = objectMapper.readValue(json, UserResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);

        verify(userService).getUserById(userId);
    }

    @Test
    @WithMockUser(authorities = {"USER", "COMPANY_OWNER", "ACCOUNTANT", "ADMIN"})
    void shouldReturnUserByUsernameForAuthorizedRoles() throws Exception {
        // Given
        String username = "testUsername";
        String endpoint = "/users/" + username;
        User user = RandomUtils.createRandomUser();
        UserResponseDto expectedDto = UserDtoMapper.toUserResponseDto(user);
        when(userService.getUserByUsername(username)).thenReturn(user);

        // When-Then
        MvcResult result = mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserResponseDto actualDto = objectMapper.readValue(json, UserResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);

        verify(userService).getUserByUsername(username);
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void shouldUpdateUserPersonalDataForAuthorizedRoles() throws Exception {
        // Given
        String endpoint = "/users";
        User user = RandomUtils.createRandomUser();
        UserResponseDto expectedDto = UserDtoMapper.toUserResponseDto(user);
        when(userService.updateUserPersonalData(any(User.class))).thenReturn(user);

        // When-Then
        MvcResult result = mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateUserPersonalDataRequestDto(user)))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        UserResponseDto actualDto = objectMapper.readValue(json, UserResponseDto.class);
        assertThat(actualDto).isEqualTo(expectedDto);

        verify(userService).updateUserPersonalData(any(User.class));
    }

    @Test
    @WithMockUser(authorities = {"USER", "ADMIN"})
    void shouldDeleteUserForAuthorizedRoles() throws Exception {
        // Given
        String username = "testUsername";
        String endpoint = "/users/" + username;

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isOk());

        verify(userService).deleteUserByUsername(username);
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnGetAllUsers() throws Exception {
        // Given
        String endpoint = "/users";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnGetUserById() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        String endpoint = "/users/id/" + userId;

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnGetUserByUsername() throws Exception {
        // Given
        String username = "testUsername";
        String endpoint = "/users/" + username;

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnUpdateUserPersonalData() throws Exception {
        // Given
        String endpoint = "/users";
        User user = RandomUtils.createRandomUser();

        // When-Then
        mockMvc.perform(put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUpdateUserPersonalDataRequestDto(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnForbiddenForUnauthorizedUserOnDeleteUser() throws Exception {
        // Given
        String username = "testUsername";
        String endpoint = "/users/" + username;

        // When-Then
        mockMvc.perform(delete(endpoint))
                .andExpect(status().isForbidden());
    }
}
