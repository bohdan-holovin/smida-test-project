package com.holovin.smidatestproject.service;


import com.holovin.smidatestproject.exceptions.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.model.UserDetails;
import com.holovin.smidatestproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = repository.findByUsername(username);

        return userDetail.map(UserDetails::new)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
