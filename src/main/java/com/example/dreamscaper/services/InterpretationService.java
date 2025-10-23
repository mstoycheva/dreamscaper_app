package com.example.dreamscaper.services;

import com.example.dreamscaper.models.Interpretation;
import com.example.dreamscaper.repositories.InterpretationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InterpretationService {
    private final InterpretationRepository interpretationRepository;

    public InterpretationService(InterpretationRepository interpretationRepository) {
        this.interpretationRepository = interpretationRepository;
    }

    public List<Interpretation> getInterpretations() {
        return interpretationRepository.findAll();
    }

    public Interpretation getInterpretation(UUID id) {
        Optional<Interpretation> interpretation = interpretationRepository.findById(id);
        return interpretation.orElse(null);
    }

    public void createInterpretation(Interpretation interpretation) {
        interpretationRepository.saveAndFlush(interpretation);
    }

    public void editInterpretation(Interpretation interpretation) {
        interpretationRepository.save(interpretation);
    }

    public void deleteInterpretation(UUID id) {
        interpretationRepository.deleteById(id);
    }
}
