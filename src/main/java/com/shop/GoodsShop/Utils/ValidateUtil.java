package com.shop.GoodsShop.Utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidateUtil {
    public static Map<String, String> validate(BindingResult bindingResult) {
        Map<String, String> errors = null;
        if (bindingResult.hasErrors()) {
            errors = bindingResult
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                fieldError -> fieldError.getField() + "Error",
                                FieldError::getDefaultMessage));
        } else {
            errors = new HashMap<>();
        }

        return errors;
    }
}
