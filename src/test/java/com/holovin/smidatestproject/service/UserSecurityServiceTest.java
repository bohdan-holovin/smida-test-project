package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.AbstractUnitTest;
import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.Role;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.repository.UserRepository;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserSecurityServiceTest extends AbstractUnitTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserSecurityService userSecurityService;

    private User user;

    @BeforeEach
    void setUp() {
        user = RandomUtils.createRandomUser();
    }

    @Test
    void shouldLoadUserByUsernameWhenUserExists() {
        // Given
        given(repository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

        // When
        UserDetails actualUserDetails = userSecurityService.loadUserByUsername(user.getUsername());

        // Then
        assertThat(actualUserDetails).isNotNull();
        assertThat(actualUserDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(actualUserDetails.getPassword()).isEqualTo(user.getPassword());

        verify(repository).findByUsername(user.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // Given
        given(repository.findByUsername(user.getUsername())).willReturn(Optional.empty());

        // When-Then
        assertThatThrownBy(() -> userSecurityService.loadUserByUsername(user.getUsername()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(user.getUsername());
        verify(repository).findByUsername(user.getUsername());
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        PasswordEncoder customEncoder = new BCryptPasswordEncoder();
        User expectedUser = getExpectedUser(customEncoder);

        given(encoder.encode(user.getPassword())).willReturn(user.getPassword());
        given(repository.save(user)).willReturn(expectedUser);

        // When
        User actualUser = userSecurityService.registerUser(user);

        // Then
        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());

        verify(encoder).encode(user.getPassword());
        verify(repository).save(user);
    }

    private User getExpectedUser(PasswordEncoder customEncoder) {
        return new User(
                user.getId(),
                user.getCreatedDate(),
                user.getLastModifiedDate(),
                user.getCompany(),
                Set.of(Role.USER),
                user.getUsername(),
                customEncoder.encode(user.getPassword()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getDateOfBirth(),
                user.getDateOfStartWork(),
                user.getPosition()
        );
    }
}
