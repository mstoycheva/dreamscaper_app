package com.example.dreamscaper.controllers;

import com.example.dreamscaper.services.DreamService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/")
public class DreamRestController {
    private final DreamService dreamService;

    public DreamRestController(DreamService dreamService) {
        this.dreamService = dreamService;
    }

    @DeleteMapping(value = "/dreams/{id}")
    public void deleteDream(@PathVariable UUID id) {
        dreamService.deleteDream(id);
    }
}
