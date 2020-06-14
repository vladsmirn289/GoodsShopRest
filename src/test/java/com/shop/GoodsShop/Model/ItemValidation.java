package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemValidation {
    private Validator validator;
    private Item item;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        this.item = new Book("Spring 5", 50L, 1.592D, 3300D,
                "..........", UUID.randomUUID().toString(), books, "James", "K", 150, "123");
    }

    @Test
    void shouldValidateWithCorrectData() {
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void shouldNotValidateWithBlankName() {
        item.setName("  ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("Name cannot be empty");
    }

    @Test
    void shouldNotValidateWithSmallDescription() {
        item.setDescription("...");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("description");
        assertThat(violation.getMessage()).isEqualTo("Description must be between 10 and 5000 symbols");
    }

    @Test
    void shouldNotValidateWithBlankCode() {
        item.setCode("  ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("code");
        assertThat(violation.getMessage()).isEqualTo("Code must be set");
    }

    @Test
    void shouldNotValidateWithNullCategory() {
        item.setCategory(null);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("category");
        assertThat(violation.getMessage()).isEqualTo("Category cannot be null");
    }

    @Test
    void shouldNotValidateWithNegativeCount() {
        item.setCount(-1L);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("count");
        assertThat(violation.getMessage()).isEqualTo("Count must be positive or zero");
    }

    @Test
    void shouldNotValidateWithNegativeWeight() {
        item.setWeight(-1D);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("weight");
        assertThat(violation.getMessage()).isEqualTo("Weight must be positive");
    }

    @Test
    void shouldNotValidateWithZeroWeight() {
        item.setWeight(0D);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("weight");
        assertThat(violation.getMessage()).isEqualTo("Weight must be positive");
    }

    @Test
    void shouldNotValidateWithNegativePrice() {
        item.setPrice(-1D);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
        assertThat(violation.getMessage()).isEqualTo("Price must be positive");
    }

    @Test
    void shouldNotValidateWithZeroPrice() {
        item.setPrice(0D);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("price");
        assertThat(violation.getMessage()).isEqualTo("Price must be positive");
    }
}
