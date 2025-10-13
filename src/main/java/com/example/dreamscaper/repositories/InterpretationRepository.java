package com.example.dreamscaper.repositories;
import com.example.dreamscaper.models.Interpretation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterpretationRepository extends JpaRepository<Interpretation, Integer> {
}
