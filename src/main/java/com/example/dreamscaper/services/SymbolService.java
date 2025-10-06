package com.example.dreamscaper.services;

import com.example.dreamscaper.models.Symbol;
import com.example.dreamscaper.repositories.SymbolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SymbolService {
    private final SymbolRepository symbolRepository;

    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public List<Symbol> getSymbols() {
        return symbolRepository.findAll();
    }

    public Symbol getSymbol(UUID id) {
        Optional<Symbol> symbol = symbolRepository.findById(id);
        return symbol.orElse(null);
    }

    public boolean isSymbolNameAvailable(String symbolName) {
        return symbolRepository.findByName(symbolName) == null;
    }

    public void createSymbol(Symbol symbol) {
        symbolRepository.saveAndFlush(symbol);
    }

    public void editSymbol(Symbol symbol) {
        symbolRepository.save(symbol);
    }

    public void deleteSymbol(UUID id) {
        symbolRepository.deleteById(id);
    }

}
