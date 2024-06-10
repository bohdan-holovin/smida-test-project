package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exceptions.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.model.UserDetails;
import com.holovin.smidatestproject.repository.UserRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserDetailsService userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = RandomUtils.createUser();
    }

    @Test
    void shouldLoadUserByUsernameWhenUserExists() {
        // Given
        given(repository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // When
        org.springframework.security.core.userdetails.UserDetails result =
                userDetailsService.loadUserByUsername(user.getUsername());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());

        verify(repository).findByUsername(user.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // Given
        given(repository.findByUsername(user.getUsername())).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(user.getUsername()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(user.getUsername());
        verify(repository).findByUsername(user.getUsername());
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        PasswordEncoder customEncoder = new BCryptPasswordEncoder();
        User userDb = new User(user.getId(), user.getUsername(), customEncoder.encode(user.getPassword()), "USER");

        given(encoder.encode(user.getPassword())).willReturn(user.getPassword());
        given(repository.save(user)).willReturn(userDb);

        // When
        User result = userDetailsService.registerUser(user);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo(userDb.getPassword());

        verify(encoder).encode(user.getPassword());
        verify(repository).save(user);
    }
}
