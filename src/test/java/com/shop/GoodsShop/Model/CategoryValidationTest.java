package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryValidationTest {
    private Validator validator;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;
    }

    @Test
    void ShouldValidateWithCorrectName() {
        Category category = new Category("Book");

        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void ShouldNotValidateWithBlankName() {
        Category category = new Category("   ");

        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Category> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("Category name cannot be empty");
    }
}
