package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User updateUserPersonalData(User updatedUser) throws UserNotFoundException {
        return userRepository.findByUsername(updatedUser.getUsername())
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPhone(updatedUser.getPhone());
                    user.setAddress(updatedUser.getAddress());
                    user.setDateOfBirth(updatedUser.getDateOfBirth());
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException(updatedUser.getUsername()));
    }

    public void deleteUserByUsername(String username) throws UserNotFoundException {
        userRepository.findByUsername(username)
                .ifPresentOrElse(
                        (it) -> userRepository.deleteUserByUsername(username),
                        () -> { throw new UserNotFoundException(username); }
                );
    }
}
