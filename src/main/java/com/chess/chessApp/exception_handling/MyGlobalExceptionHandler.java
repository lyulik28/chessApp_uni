package com.chess.chessApp.exception_handling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyGlobalExceptionHandler {
    @ExceptionHandler
    public String handleUserAlreadyExistException(UserAlreadyExistException exception, Model model){
        model.addAttribute("exception", exception.getMessage());
        return "registration-exception";
    }
}
