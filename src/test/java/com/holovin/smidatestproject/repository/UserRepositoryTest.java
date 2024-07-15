package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.Company;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.utils.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.holovin.smidatestproject.utils.RandomUtils.createRandomCompany;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @AfterEach
    public void setUp() {
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void shouldReturnUserWhenCallFindByUsername() {
        // Given
        Company company = companyRepository.save(createRandomCompany());
        User expectedUser = userRepository.save(RandomUtils.createRandomUser(company));

        // When
        Optional<User> actualUser = userRepository.findByUsername(expectedUser.getUsername());

        // Then
        assertThat(actualUser).isPresent();
        assertUser(actualUser.get(), expectedUser);
    }

    @Test
    public void shouldReturnUserWhenCallFindById() {
        // Given
        Company company = companyRepository.save(createRandomCompany());
        User expectedUser = userRepository.save(RandomUtils.createRandomUser(company));

        // When
        Optional<User> actualUser = userRepository.findById(expectedUser.getId());

        // Then
        assertThat(actualUser).isPresent();
        assertUser(actualUser.get(), expectedUser);
    }

    @Test
    public void shouldReturnUsersWhenCallFindAll() {
        // Given
        Company company1 = companyRepository.save(createRandomCompany());
        User expectedUser1 = userRepository.save(RandomUtils.createRandomUser(company1));
        Company company2 = companyRepository.save(createRandomCompany());
        User expectedUser2 = userRepository.save(RandomUtils.createRandomUser(company2));

        // When
        List<User> actualUsers = userRepository.findAll();

        // Then
        assertThat(actualUsers).hasSize(2);
        assertUser(actualUsers.get(0), expectedUser1);
        assertUser(actualUsers.get(1), expectedUser2);
    }

    @Test
    public void shouldDeleteUserWhenCallDeleteById() {
        // Given
        Company company = companyRepository.save(createRandomCompany());
        User user = userRepository.save(RandomUtils.createRandomUser(company));

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
