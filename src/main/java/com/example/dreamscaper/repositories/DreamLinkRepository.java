package com.example.dreamscaper.repositories;

import com.example.dreamscaper.models.DreamLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DreamLinkRepository extends JpaRepository<DreamLink, UUID> {
}
