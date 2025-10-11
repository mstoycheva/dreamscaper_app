package com.example.dreamscaper.controllers;

import com.example.dreamscaper.models.User;
import com.example.dreamscaper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${sync.uploads.folder:/tmp}")
    String uploadDirectory;

    @GetMapping("users/add")
    public String createUser(Model model) {
        model.addAttribute("user",new User());
        return "add-user";
    }

    @PostMapping(path = "/users/add", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitUserForm(User user) {
        userService.createUser(user);
        return new RedirectView("/users/add");
    }

    @PostMapping(path = "/users", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView submitUser(User user) {
        if (userService.isUsernameAvailable(user.getUsername())) {
            userService.createUser(user);
            return new RedirectView("/users");
        } else {
            return new RedirectView("users?error=username_exists");
        }
    }

    @GetMapping(value = "/users/edit-user/{id}")
    public String editUser(@PathVariable UUID id, Model model) {
        User editUser = userService.getUser(id);
        model.addAttribute("user", editUser);
        return "edit-user";
    }

    @PostMapping(value = "/users/edit-user")
    public RedirectView editSubmitUser(@ModelAttribute(value = "user") User user) {
        userService.editUser(user);
        return new RedirectView("/users");
    }

    @GetMapping(value = "users")
    public String usersView(Model model){
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/full-info-user/{id}")
    public String viewUser(@PathVariable UUID id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "full-info-user";
    }

    @PostMapping("/full-info-user")
    public String updateUserInfo(@ModelAttribute("user") User updatedUser) {
        userService.editUser(updatedUser);
        return "redirect:/user-own-page";
    }


    @GetMapping(value = "/profile")
    public String profile(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", userService.getUser(currentUser.getId()));
        return "profile";
    }

    @PostMapping(value = "/profile-picture")
    public RedirectView changeProfilePicture(@RequestParam("image") MultipartFile image, Authentication authentication) throws IOException  {
        User currentUser = (User) authentication.getPrincipal();
        Path fileNameAndPath = Paths.get(uploadDirectory, currentUser.getId() + "." + getExtensionByStringHandling(image.getOriginalFilename()));
        deleteExistingProfilePics(currentUser.getId());
        Files.write(fileNameAndPath, image.getBytes());
        return new RedirectView("/profile");
    }

    @GetMapping(value = "/profile-picture", produces = "image/*")
    public ResponseEntity<ByteArrayResource> getUserProfilePicture(Authentication authentication) throws IOException {
        User currentUser = (User) authentication.getPrincipal();
        UUID id = currentUser.getId();
        File dir = new File(uploadDirectory);
        FilenameFilter filter = (dir1, name) -> name.contains(String.valueOf(id));
        String[] files = dir.list(filter);

        try (InputStream defaultFs = getClass().getClassLoader().getResourceAsStream("static/images/default-user.png")) {
            if (defaultFs != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(new ByteArrayResource(defaultFs.readAllBytes()));
            }
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Failed to load default profile picture.", e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private void deleteExistingProfilePics(UUID userId) {
        File dir = new File(uploadDirectory);
        FilenameFilter filter = (dir1, name) -> name.contains(String.valueOf(userId) + ".");
        String[] files = dir.list(filter);
        if (files != null) {
            for (String file: files) {
                Logger.getAnonymousLogger().info("Deleting profile picture " + file);
                File f = new File(uploadDirectory + File.separator + file);
                f.delete();
            }
        }
    }

    private String getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1)).orElse(null);
    }
}

