package com.example.dreamscaper.controllers;

import com.example.dreamscaper.models.Interpretation;
import com.example.dreamscaper.services.InterpretationService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

public class InterpretationController {
    private final InterpretationService interpretationService;

    public InterpretationController(InterpretationService interpretationService) {
        this.interpretationService = interpretationService;
    }

    @GetMapping(value = "/interpretations")
    public String viewInterpretations(Model model) {
        model.addAttribute("interpretations", interpretationService.getInterpretations());
        return "interpretations";
    }

    @GetMapping("/interpretations/add")
    public String addInterpretation(Model model) {
        model.addAttribute("interpretation", new Interpretation());
        return "add-interpretation";
    }

    @PostMapping(path = "/interpretations/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitAllInterpretationForm(Interpretation interpretation) {
        interpretationService.createInterpretation(interpretation);
        return new RedirectView("/interpretations");
    }

    @GetMapping("/interpretations/edit/{id}")
    public String editInterpretation(@PathVariable UUID id, Model model) {
        Interpretation editInterpretation = interpretationService.getInterpretation(id);
        model.addAttribute("interpretation", editInterpretation);
        return "edit-interpretation";
    }

    @PostMapping("/interpretations/edit")
    public RedirectView submitInterpretationEditForm(@ModelAttribute("interpretation") Interpretation interpretation, @AuthenticationPrincipal UserDetails userDetails) {
        interpretationService.editInterpretation(interpretation);
        return new RedirectView("/user-own-page");
    }
}
