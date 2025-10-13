package com.example.dreamscaper.repositories;

import com.example.dreamscaper.models.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SymbolRepository extends JpaRepository<Symbol, UUID> {
    Symbol findByName(String name);
}
