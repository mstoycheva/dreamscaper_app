package com.example.dreamscaper.services;

import com.example.dreamscaper.models.DreamLink;
import com.example.dreamscaper.repositories.DreamLinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DreamLinkService {
    private final DreamLinkRepository dreamLinkRepository;

    public DreamLinkService(DreamLinkRepository dreamLinkRepository) {
        this.dreamLinkRepository = dreamLinkRepository;
    }

    public List<DreamLink> getDreams() {
        return dreamLinkRepository.findAll();
    }

    public DreamLink getDream(UUID id) {
        Optional<DreamLink> dreamLink = dreamLinkRepository.findById(id);
        return dreamLink.orElse(null);
    }

    public void createDream(DreamLink dreamLink) {
        dreamLinkRepository.saveAndFlush(dreamLink);
    }

    public void editDream(DreamLink dreamLink) {
        dreamLinkRepository.save(dreamLink);
    }

    public void deleteDream(UUID id) {
        dreamLinkRepository.deleteById(id);
    }
}
