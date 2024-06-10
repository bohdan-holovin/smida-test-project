package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUserWhenCallFindByUsername() {
        // Given
        User expectedUser = RandomUtils.createUser();
        userRepository.save(expectedUser);

        // When
        Optional<User> actualUser = userRepository.findByUsername(expectedUser.getUsername());

        // Then
        assertThat(actualUser).isPresent();
        assertUser(actualUser.get(), expectedUser);
    }

    @Test
    public void shouldReturnUserWhenCallFindById() {
        // Given
        User expectedUser = RandomUtils.createUser();
        userRepository.save(expectedUser);

        // When
        Optional<User> actualUser = userRepository.findById(expectedUser.getId());

        // Then
        assertThat(actualUser).isPresent();
        assertUser(actualUser.get(), expectedUser);
    }

    @Test
    public void shouldReturnUsersWhenCallFindAll() {
        // Given
        User user1 = RandomUtils.createUser();
        User user2 = RandomUtils.createUser();

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> actualUsers = userRepository.findAll();

        // Then
        assertThat(actualUsers).hasSize(2);
        assertUser(actualUsers.get(0), user1);
        assertUser(actualUsers.get(1), user2);
    }

    @Test
    public void shouldDeleteUserWhenCallDeleteById() {
        // Given
        User user = RandomUtils.createUser();
        userRepository.save(user);

        // When
        userRepository.deleteById(user.getId());
        Optional<User> actualOptionalUser = userRepository.findById(user.getId());

        // Then
        assertThat(actualOptionalUser).isNotPresent();
    }

    private static void assertUser(User actualUser, User expectedUser) {
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
    }
}
