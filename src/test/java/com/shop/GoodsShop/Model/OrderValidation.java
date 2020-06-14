package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderValidation {
    private Validator validator;
    private Order order;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "...", UUID.randomUUID().toString(), books);
        Set<Item> orderedItems = new HashSet<>();
        orderedItems.add(item);

        Contacts contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");

        this.order = new Order(orderedItems, 2L, contacts, "C.O.D");
    }

    @Test
    void ShouldValidateWithCorrectData() {
        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void ShouldNotValidateWithEmptyOrderedItems() {
        order.setOrderedItems(new HashSet<>());

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("orderedItems");
        assertThat(violation.getMessage()).isEqualTo("Ordered items cannot be empty");
    }

    @Test
    void ShouldNotValidateWithNegativeOrderedCount() {
        order.setOrderedCount(-1L);

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("orderedCount");
        assertThat(violation.getMessage()).isEqualTo("Order count must be greater than 0");
    }

    @Test
    void ShouldNotValidateWithZeroOrderedCount() {
        order.setOrderedCount(0L);

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("orderedCount");
        assertThat(violation.getMessage()).isEqualTo("Order count must be greater than 0");
    }

    @Test
    void ShouldNotValidateWithNullContacts() {
        order.setContacts(null);

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("contacts");
        assertThat(violation.getMessage()).isEqualTo("Contacts must be set");
    }

    @Test
    void ShouldNotValidateWithBlankPaymentMethod() {
        order.setPaymentMethod("   ");

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("paymentMethod");
        assertThat(violation.getMessage()).isEqualTo("Payment method cannot be empty");
    }
}
