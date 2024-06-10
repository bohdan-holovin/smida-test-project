package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    public void shouldReturnUserWhenMappingFromRegisterUserRequestDto() {
        // Given
        UserRegisterRequestDto dto = new UserRegisterRequestDto();
        dto.setUsername("testUser");
        dto.setPassword("testPassword");
        String roles = "USER ADMIN";
        dto.setRoles(roles);

        // When
        User user = UserMapper.ToUser(dto);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testUser");
        assertThat(user.getPassword()).isEqualTo("testPassword");
        assertThat(user.getRoles()).isEqualTo(roles);
    }
}
