package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.Role;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.model.UserDetailsImpl;
import com.holovin.smidatestproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER));
        return userRepository.save(user);
    }

    public User updateUserPassword(User updatedUser) throws UserNotFoundException {
        return userRepository.findByUsername(updatedUser.getUsername())
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(updatedUser.getId())));
    }
}