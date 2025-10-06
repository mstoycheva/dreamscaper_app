package com.example.dreamscaper.services;

import com.example.dreamscaper.models.Dream;
import com.example.dreamscaper.models.User;
import com.example.dreamscaper.repositories.DreamRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DreamService {
    private final DreamRepository dreamRepository;
    private final UserService userService;
    public DreamService(DreamRepository dreamRepository, UserService userService) {
        this.dreamRepository = dreamRepository;
        this.userService = userService;
    }

    public List<Dream> getDreams() {
        return dreamRepository.findAll();
    }

    public Dream getDream(UUID id) {
        Optional<Dream> dream = dreamRepository.findById(id);
        return dream.orElse(null);
    }

    public List<Dream> findByUser(User user) {
        return dreamRepository.findByUser(user);
    }

    public List<Dream> getDreamsByUsername(String username) {
        return dreamRepository.findByUserUsername(username);
    }

    public void createDream(Dream dream) {
        dreamRepository.saveAndFlush(dream);
    }

    public Dream createDreamForUser(Dream dream, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        dream.setUser(user);
        return dreamRepository.save(dream);
    }

    public void editDream(Dream dream) {
        dreamRepository.save(dream);
    }

    public void deleteDream(UUID id) {
        dreamRepository.deleteById(id);
    }
}
