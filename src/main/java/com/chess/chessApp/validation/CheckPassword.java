package com.chess.chessApp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPasswordValidator.class)
public @interface CheckPassword {
    public String value() default "^(?=.*[A-Z])(?=.*[a-z].*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$";
    public String message() default "Password must contain at least one uppercase Latin letter, more than one lowercase Latin letter, at least one number and at least one special character";
    public Class<?>[] groups() default {};
    public Class<? extends Payload> [] payload() default {};
}
