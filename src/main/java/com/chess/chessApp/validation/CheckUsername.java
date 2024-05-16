package com.chess.chessApp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUsernameValidator.class)
public @interface CheckUsername {
    public String value() default "^[a-zA-Z0-9_]*$";
    public String message() default "Username must consist only of Latin letters, numbers and an underscore";
    public Class<?>[] groups() default {};
    public Class<? extends Payload> [] payload() default {};
}
