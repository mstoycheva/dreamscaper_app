package com.example.dreamscaper.controllers;

import com.example.dreamscaper.dto.SignInDTO;
import com.example.dreamscaper.services.UserService;
import com.example.dreamscaper.models.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/")
public class MainPageController {

    @GetMapping(value = "/starting")
    public String showStaringPage() {
        return "starting-page";
    }



}
