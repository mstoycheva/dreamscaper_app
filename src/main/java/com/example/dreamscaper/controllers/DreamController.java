package com.example.dreamscaper.controllers;

import com.example.dreamscaper.models.Dream;
import com.example.dreamscaper.models.User;
import com.example.dreamscaper.services.DreamService;
import com.example.dreamscaper.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/")
public class DreamController {

    private final DreamService dreamService;
    private final UserService userService;

    public DreamController(DreamService dreamService, UserService userService) {
        this.dreamService = dreamService;
        this.userService = userService;
    }

    @PostMapping("/dreams")
    public ResponseEntity<Dream> createDream(@RequestBody Dream dream,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Dream created = dreamService.createDreamForUser(dream, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/dreams")
    public ResponseEntity<?> getUserDreams(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        return ResponseEntity.ok(dreamService.getDreamsByUsername(userDetails.getUsername()));
    }

    @GetMapping("/dreams/add")
    public String addDream(Model model) {
        model.addAttribute("dream", new Dream());
        return "add-dream";
    }

    @PostMapping(path = "/dreams/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitDreamForm(Dream dream, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        dream.setUser(currentUser);
        dreamService.createDream(dream);
        return new RedirectView("/dreams/add");
    }

    @GetMapping("/dreams/edit/{id}")
    public String editDream(@PathVariable UUID id, Model model) {
        Dream editDream = dreamService.getDream(id);
        model.addAttribute("dream", editDream);
        return "edit-dream";
    }

    @PostMapping("/dreams/edit")
    public RedirectView submitDreamEditForm(@ModelAttribute("dream") Dream dream, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        dream.setUser(currentUser);
        dreamService.editDream(dream);
        return new RedirectView("/user-own-page");
    }


    @GetMapping("/user-own-page")
    public String userOwnPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", currentUser);
        List<Dream> dreams = dreamService.findByUser(currentUser);
        model.addAttribute("dreams", dreams);
        return "user-own-page";
    }


    @PostMapping(path = "/user-own-page", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitForm(Dream dream, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        dream.setUser(currentUser);
        dreamService.createDream(dream);
        return new RedirectView("/user-own-page");
    }

    @GetMapping("/full-info-dream/{id}")
    public String editUser(@PathVariable UUID id, Model model) {
        Dream dream = dreamService.getDream(id);
        model.addAttribute("dream", dream);
        return "full-info-dream";
    }

    @PostMapping("/full-info-dream")
    public String updateDreamInfo(@ModelAttribute("dream") Dream updatedDream) {
        dreamService.editDream(updatedDream);
        return "redirect:/user-own-page";
    }

}
