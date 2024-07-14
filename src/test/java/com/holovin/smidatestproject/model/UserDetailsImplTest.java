package com.holovin.smidatestproject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsImplTest {

    private UserDetailsImpl userDetailsImpl;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles(Set.of(Role.values()));
        userDetailsImpl = new UserDetailsImpl(user);
    }

    @Test
    void shouldReturnCorrectMapAuthorities() {
        Collection<? extends GrantedAuthority> actualAuthorities = userDetailsImpl.getAuthorities();
        assertThat(actualAuthorities.size()).isEqualTo(4);
        assertTrue(actualAuthorities.contains(new SimpleGrantedAuthority("USER")));
        assertTrue(actualAuthorities.contains(new SimpleGrantedAuthority("ADMIN")));
        assertTrue(actualAuthorities.contains(new SimpleGrantedAuthority("COMPANY_OWNER")));
        assertTrue(actualAuthorities.contains(new SimpleGrantedAuthority("ACCOUNTANT")));
    }

    @Test
    void shouldReturnCorrectMapPassword() {
        assertEquals("testPassword", userDetailsImpl.getPassword());
    }

    @Test
    void shouldReturnCorrectMapUserName() {
        assertEquals("testUser", userDetailsImpl.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userDetailsImpl.isEnabled());
    }
}
