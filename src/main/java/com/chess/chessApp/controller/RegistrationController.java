package com.chess.chessApp.controller;

import com.chess.chessApp.entity.User;
import com.chess.chessApp.exception_handling.UserAlreadyExistException;
import com.chess.chessApp.repository.FriendsRepository;
import com.chess.chessApp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showEnterPage() {
        return "main";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/submitRegistration")
    public String submitRegistration(@Valid @ModelAttribute("user") User user,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("This username is already taken");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistException("User with this email already exists");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setVictories(0);
        user.setPlayed(0);
        userRepository.save(user);
        return "submit-registration";
    }
}
