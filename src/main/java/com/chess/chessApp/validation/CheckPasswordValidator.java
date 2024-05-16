package com.chess.chessApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {
    private String passwordPattern;
    private Pattern pattern;
    @Override
    public void initialize(CheckPassword checkPassword) {
        passwordPattern = checkPassword.value();
        pattern = Pattern.compile(passwordPattern);
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = pattern.matcher(enteredValue);
        return matcher.matches();
    }
}
