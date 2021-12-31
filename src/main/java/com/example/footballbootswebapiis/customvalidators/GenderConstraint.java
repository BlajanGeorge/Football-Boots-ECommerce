package com.example.footballbootswebapiis.customvalidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {
    String message() default "Invalid gender format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
