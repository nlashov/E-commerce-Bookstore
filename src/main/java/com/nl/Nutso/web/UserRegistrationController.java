package com.nl.Nutso.web;

import com.nl.Nutso.model.dto.UserRegistrationDTO;
import com.nl.Nutso.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDTO userRegistrationDTO) {

        // TODO: Registration email with activation link

        userService.registerUser(userRegistrationDTO);

        return "redirect:/users/login";
    }



}
