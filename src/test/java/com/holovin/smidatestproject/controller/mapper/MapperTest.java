package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.RegisterUserRequestDto;
import com.holovin.smidatestproject.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {

    @Test
    public void givenRegisterUserRequestDto_whenMapToUser_thenReturnUser() {
        // Given
        RegisterUserRequestDto dto = new RegisterUserRequestDto();
        dto.setUsername("testUser");
        dto.setPassword("testPassword");
        String roles = "USER ADMIN";
        dto.setRoles(roles);

        // When
        User user = Mapper.mapToUser(dto);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("testPassword");
        assertThat(user.getRoles()).isEqualTo(roles);
    }
}
