package com.nl.Nutso.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = YearNotInFutureValidator.class)
public @interface YearNotInFuture {

    String message() default "Year cannot be in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
