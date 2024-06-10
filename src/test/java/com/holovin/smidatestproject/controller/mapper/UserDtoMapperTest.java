package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoMapperTest {

    @Test
    public void shouldReturnUserWhenMappingFromRegisterUserRequestDto() {
        // Given
        UserRegisterRequestDto dto = new UserRegisterRequestDto();
        dto.setUsername("testUser");
        dto.setPassword("testPassword");
        String roles = "USER ADMIN";
        dto.setRoles(roles);

        // When
        User actualUser = UserDtoMapper.toUser(dto);

        // Then
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo(dto.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(dto.getPassword());
        assertThat(actualUser.getRoles()).isEqualTo(roles);
    }
}
