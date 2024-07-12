package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.user.request.UserRegisterRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UserUpdateRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoMapperTest {

    @Test
    public void shouldMapUserToUserResponseDto() {
        // Given
        User testUser = RandomUtils.createRandomUser();
        UserResponseDto expectedDto = new UserResponseDto();
        expectedDto.setId(testUser.getId());
        expectedDto.setUsername(testUser.getUsername());
        expectedDto.setFirstName(testUser.getFirstName());
        expectedDto.setLastName(testUser.getLastName());
        expectedDto.setEmail(testUser.getEmail());
        expectedDto.setPhone(testUser.getPhone());
        expectedDto.setAddress(testUser.getAddress());

        // When
        UserResponseDto actualDto = UserDtoMapper.toUserResponseDto(testUser);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void shouldMapUserUpdateRequestDtoToUser() {
        // Given
        User expectedUser = RandomUtils.createRandomUser();
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
        requestDto.setId(expectedUser.getId());
        requestDto.setUsername(expectedUser.getUsername());
        requestDto.setPassword(expectedUser.getPassword());
        requestDto.setFirstName(expectedUser.getFirstName());
        requestDto.setLastName(expectedUser.getLastName());
        requestDto.setEmail(expectedUser.getEmail());
        requestDto.setPhone(expectedUser.getPhone());
        requestDto.setAddress(expectedUser.getAddress());

        // When
        User actualUser = UserDtoMapper.toUser(requestDto);

        // Then
        assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPhone()).isEqualTo(expectedUser.getPhone());
        assertThat(actualUser.getAddress()).isEqualTo(expectedUser.getAddress());
    }

    @Test
    public void shouldMapUserRegisterRequestDtoToUser() {
        // Given
        User expectedUser = RandomUtils.createRandomUser();
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setUsername(expectedUser.getUsername());
        requestDto.setPassword(expectedUser.getPassword());
        requestDto.setFirstName(expectedUser.getFirstName());
        requestDto.setLastName(expectedUser.getLastName());
        requestDto.setEmail(expectedUser.getEmail());
        requestDto.setPhone(expectedUser.getPhone());
        requestDto.setAddress(expectedUser.getAddress());

        // When
        User actualUser = UserDtoMapper.toUser(requestDto);

        // Then
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPhone()).isEqualTo(expectedUser.getPhone());
        assertThat(actualUser.getAddress()).isEqualTo(expectedUser.getAddress());
    }
}
