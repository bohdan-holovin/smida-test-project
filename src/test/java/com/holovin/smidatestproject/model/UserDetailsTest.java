package com.holovin.smidatestproject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsTest {

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRoles("USER ADMIN");

        userDetails = new UserDetails(user);
    }

    @Test
    void shouldCorrectMapAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertThat(authorities.size()).isEqualTo(2);
        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ADMIN")));
    }

    @Test
    void shouldCorrectMapPassword() {
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    void shouldCorrectMapUserName() {
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}
