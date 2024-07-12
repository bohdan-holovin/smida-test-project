package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User updateUser(User updatedUser) throws UserNotFoundException {
        return Optional.of(getUserById(updatedUser.getId()))
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPhone(updatedUser.getPhone());
                    user.setAddress(updatedUser.getAddress());
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(updatedUser.getId())));
    }

    public void deleteUserById(int id) throws UserNotFoundException {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
