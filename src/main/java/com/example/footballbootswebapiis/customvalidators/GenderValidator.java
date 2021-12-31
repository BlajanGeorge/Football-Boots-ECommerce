package com.example.footballbootswebapiis.customvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<GenderConstraint, String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender.equals("M") || gender.equals("F");
    }

    public static boolean isValid(String gender) {
        return gender.equals("M") || gender.equals("F");
    }
}
