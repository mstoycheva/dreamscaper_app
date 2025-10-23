package com.example.dreamscaper.repositories;
import com.example.dreamscaper.models.Interpretation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterpretationRepository extends JpaRepository<Interpretation, UUID> {
}
