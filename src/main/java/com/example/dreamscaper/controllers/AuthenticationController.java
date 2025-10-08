package com.example.dreamscaper.controllers;

import com.example.dreamscaper.dto.SignInDTO;
import com.example.dreamscaper.models.User;
import com.example.dreamscaper.services.AuthenticationService;
import com.example.dreamscaper.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GetMapping(value = "/sign-in")
    public String showSignInPage(Model model) {
        return "sign-in";
    }

    @GetMapping(value = "/sign-up")
    public String signUpUser(Model model) {
        model.addAttribute("user",new User());
        return "sign-up";
    }

    @PostMapping(path = "/sign-up", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String submitUser(@ModelAttribute User user) {
        boolean isUsernameAvailable = userService.isUsernameAvailable(user.getUsername());
        boolean isEmailAvailable = userService.isEmailAvailable(user.getEmail());

        if (!isUsernameAvailable) {
            return "redirect:/sign-up?error=username_exists";
        }

        if (!isEmailAvailable) {
            return "redirect:/sign-up?error=email_exists";
        }

        userService.createUser(user);
        return "redirect:/sign-in";
    }

}