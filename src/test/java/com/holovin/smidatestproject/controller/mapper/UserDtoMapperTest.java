package com.holovin.smidatestproject.controller.mapper;

import com.holovin.smidatestproject.controller.dto.user.request.RegisterUserRequestDto;
import com.holovin.smidatestproject.controller.dto.user.request.UpdateUserPersonalDataRequestDto;
import com.holovin.smidatestproject.controller.dto.user.response.UserResponseDto;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoMapperTest {

    @Test
    public void shouldMapUserRegisterRequestDtoToUser() {
        // Given
        User expectedUser = RandomUtils.createRandomUser();
        RegisterUserRequestDto requestDto = new RegisterUserRequestDto();
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

    @Test
    public void shouldMapUserToUserResponseDto() {
        // Given
        User testUser = RandomUtils.createRandomUser();
        UserResponseDto expectedDto = new UserResponseDto();
        expectedDto.setId(testUser.getId());
        expectedDto.setCompanyId(testUser.getCompany().getId());
        expectedDto.setUsername(testUser.getUsername());
        expectedDto.setFirstName(testUser.getFirstName());
        expectedDto.setLastName(testUser.getLastName());
        expectedDto.setEmail(testUser.getEmail());
        expectedDto.setPhone(testUser.getPhone());
        expectedDto.setAddress(testUser.getAddress());
        expectedDto.setDateOfBirth(testUser.getDateOfBirth());
        expectedDto.setDateOfStartWork(testUser.getDateOfStartWork());
        expectedDto.setPosition(testUser.getPosition());

        // When
        UserResponseDto actualDto = UserDtoMapper.toUserResponseDto(testUser);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    public void shouldMapUserUpdateRequestDtoToUser() {
        // Given
        User expectedUser = RandomUtils.createRandomUser();
        UpdateUserPersonalDataRequestDto requestDto = new UpdateUserPersonalDataRequestDto();
        requestDto.setUsername(expectedUser.getUsername());
        requestDto.setFirstName(expectedUser.getFirstName());
        requestDto.setLastName(expectedUser.getLastName());
        requestDto.setEmail(expectedUser.getEmail());
        requestDto.setPhone(expectedUser.getPhone());
        requestDto.setAddress(expectedUser.getAddress());

        // When
        User actualUser = UserDtoMapper.toUser(requestDto);

        // Then
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
        assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUser.getPhone()).isEqualTo(expectedUser.getPhone());
        assertThat(actualUser.getAddress()).isEqualTo(expectedUser.getAddress());
    }
}
