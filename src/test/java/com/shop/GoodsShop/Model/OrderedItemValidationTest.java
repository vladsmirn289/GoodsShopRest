package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderedItemValidationTest {
    private Validator validator;
    private OrderedItem orderedItem;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", UUID.randomUUID().toString(), books);
        this.orderedItem = new OrderedItem(item, 1);
    }

    @Test
    void shouldValidateWithCorrectData() {
        Set<ConstraintViolation<OrderedItem>> constraintViolations = validator.validate(orderedItem);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void shouldNotValidateWithNullItem() {
        orderedItem.setItem(null);
        Set<ConstraintViolation<OrderedItem>> constraintViolations = validator.validate(orderedItem);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<OrderedItem> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("item");
        assertThat(violation.getMessage()).isEqualTo("Item must be set");
    }

    @Test
    void shouldNotValidateWithNegativeQuantity() {
        orderedItem.setQuantity(-1);
        Set<ConstraintViolation<OrderedItem>> constraintViolations = validator.validate(orderedItem);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<OrderedItem> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
        assertThat(violation.getMessage()).isEqualTo("Quantity cannot be negative or zero");
    }

    @Test
    void shouldNotValidateWithZeroQuantity() {
        orderedItem.setQuantity(0);
        Set<ConstraintViolation<OrderedItem>> constraintViolations = validator.validate(orderedItem);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<OrderedItem> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("quantity");
        assertThat(violation.getMessage()).isEqualTo("Quantity cannot be negative or zero");
    }
}
