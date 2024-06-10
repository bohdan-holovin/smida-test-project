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
        User user = RandomUtils.createUser();
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        // Then
        assertThat(foundUser).isPresent();
        assertUser(foundUser.get(), user);
    }

    @Test
    public void shouldReturnUserWhenCallFindById() {
        // Given
        User user = RandomUtils.createUser();
        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Then
        assertThat(foundUser).isPresent();
        assertUser(foundUser.get(), user);
    }

    @Test
    public void shouldReturnUsersWhenCallFindAll() {
        // Given
        User user1 = RandomUtils.createUser();
        User user2 = RandomUtils.createUser();

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> result = userRepository.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertUser(result.get(0), user1);
        assertUser(result.get(1), user2);
    }

    @Test
    public void shouldDeleteUserWhenCallDeleteById() {
        // Given
        User user = RandomUtils.createUser();
        User savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Then
        assertThat(foundUser).isNotPresent();
    }

    private static void assertUser(User actualUser, User expectedUser) {
        assertThat(actualUser.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
    }
}
