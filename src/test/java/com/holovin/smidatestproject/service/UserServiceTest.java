package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.repository.UserRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest extends AbstractUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = RandomUtils.createRandomUser();
    }

    @Test
    void shouldReturnUsersWhenCallAllUsers() {
        // Given
        List<User> expectedUsers = List.of(testUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.getAllUsers();

        // Then
        assertThat(actualUsers).isEqualTo(expectedUsers);
        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserWhenCallFindByUserId() {
        // Given
        int userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // When
        User actualUser = userService.getUserById(userId);

        // Then
        assertThat(actualUser).isEqualTo(testUser);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCallFindByUserId() {
        // Given
        int userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldReturnUserWhenCallFindByUsername() {
        // Given
        String username = testUser.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // When
        User actualUser = userService.getUserByUsername(username);

        // Then
        assertThat(actualUser).isEqualTo(testUser);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCallFindByUsername() {
        // Given
        String username = testUser.getUsername();
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldReturnUpdatedUserWhenCallUpdateUser() {
        // Given
        int userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User actualUser = userService.updateUser(testUser);

        // Then
        assertThat(actualUser.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(actualUser.getFirstName()).isEqualTo(testUser.getFirstName());
        assertThat(actualUser.getLastName()).isEqualTo(testUser.getLastName());
        assertThat(actualUser.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(actualUser.getPhone()).isEqualTo(testUser.getPhone());
        assertThat(actualUser.getAddress()).isEqualTo(testUser.getAddress());

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCallUpdateUser() {
        // Given
        int userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(testUser));
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldDeleteUser() {
        // Given
        int userId = testUser.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUserById(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }
}
