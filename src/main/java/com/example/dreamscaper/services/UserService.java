package com.example.dreamscaper.services;

import com.example.dreamscaper.models.User;
import com.example.dreamscaper.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email) == null;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public void editUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }

//    public List<User> getUsersByFirstNameOrLastName(String firstName, String lastName) {
//        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
//            return userRepository.findByFirstNameOrLastName(firstName, lastName);
//        } else if (firstName != null && !firstName.isEmpty()) {
//            return userRepository.findByFirstName(firstName);
//        } else if (lastName != null && !lastName.isEmpty()) {
//            return userRepository.findByLastName(lastName);
//        } else {
//            return List.of();
//        }
//    }

}
