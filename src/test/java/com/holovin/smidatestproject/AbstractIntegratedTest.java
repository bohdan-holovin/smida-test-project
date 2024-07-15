package com.holovin.smidatestproject;

import com.holovin.smidatestproject.config.SecurityConfig;
import com.holovin.smidatestproject.service.JwtService;
import com.holovin.smidatestproject.service.UserSecurityService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;

@ComponentScan(basePackageClasses = {SecurityConfig.class})
public class AbstractIntegratedTest {

    @MockBean
    private UserSecurityService userSecurityService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;
}
