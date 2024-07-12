package com.holovin.smidatestproject.service;

import com.holovin.smidatestproject.exception.UserNotFoundException;
import com.holovin.smidatestproject.model.User;
import com.holovin.smidatestproject.model.UserDetailsImpl;
import com.holovin.smidatestproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
