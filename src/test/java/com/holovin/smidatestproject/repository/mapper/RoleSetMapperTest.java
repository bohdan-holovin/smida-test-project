package com.holovin.smidatestproject.repository.mapper;

import com.holovin.smidatestproject.model.Role;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleSetMapperTest {

    private final RoleSetMapper roleSetMapper = new RoleSetMapper();

    @Test
    void shouldConvertRolesSetToString() {
        // Given
        Set<Role> roles = Set.of(Role.USER, Role.ADMIN);

        // When
        String actualString = roleSetMapper.convertToDatabaseColumn(roles);

        // Then
        assertTrue(actualString.contains("USER"));
        assertTrue(actualString.contains("ADMIN"));
    }

    @Test
    void shouldConvertStringToRolesSet() {
        // Given
        String rolesString = "USER,ADMIN";
        Set<Role> expectedRoles = Set.of(Role.USER, Role.ADMIN);

        // When
        Set<Role> actualRoles = roleSetMapper.convertToEntityAttribute(rolesString);

        // Then
        assertThat(actualRoles).isEqualTo(expectedRoles);
    }

    @Test
    void shouldHandleEmptyRolesSetToEmptyString() {
        // Given
        Set<Role> roles = Set.of();
        String expectedString = "";

        // When
        String actualString = roleSetMapper.convertToDatabaseColumn(roles);

        // Then
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    void shouldHandleEmptyStringToEmptyRolesSet() {
        // Given
        String rolesString = "";
        Set<Role> expectedRoles = Set.of();

        // When
        Set<Role> actualRoles = roleSetMapper.convertToEntityAttribute(rolesString);

        // Then
        assertThat(actualRoles).isEqualTo(expectedRoles);
    }

    @Test
    void shouldHandleNullRolesSetToEmptyString() {
        // Given
        String expectedString = "";

        // When
        String actualString = roleSetMapper.convertToDatabaseColumn(null);

        // Then
        assertThat(actualString).isEqualTo(expectedString);
    }

    @Test
    void shouldHandleNullStringToEmptyRolesSet() {
        // Given
        Set<Role> expectedRoles = Set.of();

        // When
        Set<Role> actualRoles = roleSetMapper.convertToEntityAttribute(null);

        // Then
        assertThat(actualRoles).isEqualTo(expectedRoles);
    }
}
