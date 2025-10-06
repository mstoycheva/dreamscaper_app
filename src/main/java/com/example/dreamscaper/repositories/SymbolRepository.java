package com.example.dreamscaper.repositories;

import com.example.dreamscaper.models.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SymbolRepository extends JpaRepository<Symbol, UUID> {
    Symbol findByName(String name);
}
