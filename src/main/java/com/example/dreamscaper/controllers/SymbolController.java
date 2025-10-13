package com.example.dreamscaper.controllers;

import com.example.dreamscaper.models.Symbol;
import com.example.dreamscaper.services.SymbolService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Controller
@RequestMapping(value = "/")
public class SymbolController {
    private final SymbolService symbolService;

    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping(value = "/symbols")
    public String viewSymbols(Model model) {
        model.addAttribute("symbols", symbolService.getSymbols());
        return "symbols";
    }

    @GetMapping("/symbols/add")
    public String addSymbol(Model model) {
        model.addAttribute("symbol", new Symbol());
        return "add-symbol";
    }

    @PostMapping(path = "/symbols/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitAllSymbolForm(Symbol symbol) {
        symbolService.createSymbol(symbol);
        return new RedirectView("/symbols");
    }

    @GetMapping("/symbols/edit/{id}")
    public String editSymbol(@PathVariable UUID id, Model model) {
        Symbol editSymbol = symbolService.getSymbol(id);
        model.addAttribute("symbol", editSymbol);
        return "edit-symbol";
    }

    @PostMapping("/symbols/edit")
    public RedirectView submitSymbolEditForm(@ModelAttribute("symbol") Symbol symbol, @AuthenticationPrincipal UserDetails userDetails) {
        symbolService.editSymbol(symbol);
        return new RedirectView("/user-own-page");
    }
}
