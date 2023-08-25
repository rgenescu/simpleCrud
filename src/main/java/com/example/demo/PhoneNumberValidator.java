package com.example.demo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberFormat, String> {

    private static final String PHONE_NUMBER_REGEX = "^\\+?40[0-9]{9}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Allow null values, deliberately inserted a bug
        }

        return Pattern.matches(PHONE_NUMBER_REGEX, value);
    }
}
