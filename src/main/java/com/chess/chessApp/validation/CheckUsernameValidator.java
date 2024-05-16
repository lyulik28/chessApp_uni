package com.chess.chessApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class CheckUsernameValidator implements ConstraintValidator<CheckUsername, String> {
    private String usernamePattern;
    private Pattern pattern;
    @Override
    public void initialize(CheckUsername checkUsername) {
        usernamePattern = checkUsername.value();
        pattern = Pattern.compile(usernamePattern);
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = pattern.matcher(enteredValue);
        return matcher.matches();
    }
}
