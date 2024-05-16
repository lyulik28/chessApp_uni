package com.chess.chessApp.controller;

import com.chess.chessApp.details.CustomUserDetails;
import com.chess.chessApp.entity.User;
import com.chess.chessApp.exception_handling.UserAlreadyExistException;
import com.chess.chessApp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SettingsController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/settings")
    public String registration(Model model) {
        return "settings";
    }

    @PostMapping("/editUsername")
    public String editUsername(@RequestParam("username") String username, Model model) {
        if (userRepository.findByUsername(username) != null) {
            throw new UserAlreadyExistException("This username is already taken");
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");
        Matcher matcher = pattern.matcher(username);
        if(!matcher.matches()){
            model.addAttribute("username", "Username incorrect");
            return "settings";
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername());
        user.setUsername(username);
        userRepository.save(user);
        model.addAttribute("username", "Username changed");
        return "settings";
    }

    @PostMapping("/editEmail")
    public String editEmail(@RequestParam("email") String email, Model model) {
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistException("User with this email already exists");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            model.addAttribute("email", "Email incorrect");
            return "settings";
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername());
        user.setEmail(email);
        userRepository.save(user);
        model.addAttribute("email", "Email changed");
        return "settings";
    }

    @PostMapping("/editPassword")
    public String editPassword(@RequestParam("password") String password, Model model) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            model.addAttribute("password", "Password incorrect");
            return "settings";
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(customUserDetails.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        model.addAttribute("password", "Password changed");
        return "settings";
    }
}
