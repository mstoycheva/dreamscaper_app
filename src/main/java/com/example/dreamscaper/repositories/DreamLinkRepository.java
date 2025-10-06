package com.example.dreamscaper.repositories;

import com.example.dreamscaper.models.DreamLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DreamLinkRepository extends JpaRepository<DreamLink, UUID> {
}
