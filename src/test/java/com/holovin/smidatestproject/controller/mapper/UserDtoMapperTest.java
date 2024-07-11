package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.auth.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.model.Role;
import com.holovin.smidatestproject.model.User;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoMapperTest {

    @Test
    public void shouldReturnUserWhenMappingFromRegisterUserRequestDto() {
        // Given
        UserRegisterRequestDto dto = new UserRegisterRequestDto();
        dto.setUsername("testUser");
        dto.setPassword("testPassword");
        Set<UserRegisterRequestDto.Role> roles = Set.of(UserRegisterRequestDto.Role.values());
        dto.setRoles(roles);

        // When
        User actualUser = UserDtoMapper.toUser(dto);

        // Then
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getUsername()).isEqualTo(dto.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(dto.getPassword());
        assertThat(actualUser.getRoles()).isEqualTo(roles.stream()
                .map(it -> Role.valueOf(it.name()))
                .collect(Collectors.toSet()));
    }
}
