package com.chess.chessApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {
    private String emailPattern;
    private Pattern pattern;
    @Override
    public void initialize(CheckEmail checkEmail) {
        emailPattern = checkEmail.value();
        pattern = Pattern.compile(emailPattern);
    }

    @Override
    public boolean isValid(String enteredValue, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = pattern.matcher(enteredValue);
        return matcher.matches();
    }
}
