package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderedItemValidationTest {
    private Validator validator;
    private OrderedItem orderedItem;
    private Order order;
    private Item item;
    private Category books;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        this.books = new Category("Books");
        this.item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", "1234567", books);

        OrderedItem orderedItem = new OrderedItem(item, 1);
        orderedItem.setId(1L);

        Contacts contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");
        this.order = new Order(Collections.singleton(orderedItem), contacts, "C.O.D");

        orderedItem.setOrder(order);
        this.orderedItem = orderedItem;
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

    @Test
    public void shouldGetId() {
        assertThat(orderedItem.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetItem() {
        assertThat(orderedItem.getItem()).isEqualTo(item);
    }

    @Test
    public void shouldGetOrder() {
        assertThat(orderedItem.getOrder()).isEqualTo(order);
    }

    @Test
    public void shouldGetQuantity() {
        assertThat(orderedItem.getQuantity()).isEqualTo(1);
    }

    @Test
    public void shouldEqualsTrue() {
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", "1234567", books);
        OrderedItem orderedItem = new OrderedItem(item, 1);

        assertThat(this.orderedItem.equals(orderedItem)).isTrue();
    }

    @Test
    public void hashCodeTest() {
        assertThat(this.orderedItem.hashCode())
                .isEqualTo(Objects.hash(
                        item,
                        1));
    }
}
