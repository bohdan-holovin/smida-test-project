package com.holovin.smidatestproject.controller;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.config.jwt.JwtService;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static com.holovin.smidatestproject.utils.JsonUserMapperUtils.toJsonUserAuthRequestDto;
import static com.holovin.smidatestproject.utils.JsonUserMapperUtils.toJsonUserRegisterRequestDto;
import static com.holovin.smidatestproject.utils.RandomUtils.createRandomUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void shouldReturnOkForNotSecuredEndpoint() throws Exception {
        // Given
        String endpoint = "/auth/not-secured";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().string("This endpoint is not secure"))
                .andReturn();
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Given
        String endpoint = "/auth/register";

        User user = createRandomUser();

        when(userDetailsServiceImpl.registerUser(any(User.class))).thenReturn(user);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUserRegisterRequestDto(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User register successfully with username: " + user.getUsername()))
                .andReturn();
    }

    @Test
    void shouldReturnJwtTokenForValidAuthRequest() throws Exception {
        // Given
        String endpoint = "/auth/login";

        User user = createRandomUser();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("someToken");

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUserAuthRequestDto(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("someToken"))
                .andReturn();
    }

    @Test
    void shouldThrowUserNotFoundExceptionForInvalidAuthRequest() throws Exception {
        // Given
        String endpoint = "/auth/login";

        User user = createRandomUser();

        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // When-Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonUserAuthRequestDto(user)))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    void shouldReturnUserLoggedMessageForUserProfileEndpoint() throws Exception {
        // Given
        String endpoint = "/user/profile";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().string("You are logged in USER"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldReturnForbiddenForUserTryingToGetAdminProfile() throws Exception {
        // Given
        String endpoint = "/admin/profile";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "adminUser", authorities = {"ADMIN"})
    void shouldReturnAdminLoggedMessageForAdminProfileEndpoint() throws Exception {
        // Given
        String endpoint = "/admin/profile";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().string("You are logged in ADMIN"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldReturnForbiddenForAdminTryingToGetUserProfile() throws Exception {
        // Given
        String endpoint = "/user/profile";

        // When-Then
        mockMvc.perform(get(endpoint))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
