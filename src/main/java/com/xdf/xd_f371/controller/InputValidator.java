package com.xdf.xd_f371.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class InputValidator {
    private final Validator validator;

    public InputValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }
}
