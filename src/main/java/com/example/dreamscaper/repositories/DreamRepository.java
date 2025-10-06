package com.example.dreamscaper.repositories;

import com.example.dreamscaper.models.Dream;
import com.example.dreamscaper.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DreamRepository extends JpaRepository<Dream, UUID> {
    List<Dream> findByUserUsername(String username);
    List<Dream> findByUser(User user);
}
