package com.virucccc.converter.controller;

import com.virucccc.converter.model.User;
import com.virucccc.converter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpUser(User user, @RequestParam(name = "password") String pass1,
                             @RequestParam(name = "password2") String pass2, Model model) {
        User temp = userRepository.findByUsername(user.getUsername());

        if (pass1.equals("") || pass2.equals("")) {
            model.addAttribute("message", "Введите пароль");
            return signUp();
        }

        if (!pass1.equals(pass2)) {
            model.addAttribute("message", "Пароли не совпрадают");
            return signUp();
        }

        if (temp != null ) {
            model.addAttribute("message", "Пользователь уже существует");
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "redirect:/signin";
    }
}
