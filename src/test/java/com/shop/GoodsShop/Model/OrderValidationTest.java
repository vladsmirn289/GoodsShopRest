package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderValidationTest {
    private Validator validator;
    private Order order;
    private OrderedItem orderedItem;
    private Contacts contacts;
    private Client client;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "...", UUID.randomUUID().toString(), books);
        this.orderedItem = new OrderedItem(item, 1);
        Set<OrderedItem> orderedItems = new HashSet<>();
        orderedItems.add(orderedItem);

        this.contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");

        this.client = new Client("i@gmail.com", "12345", "Igor", "Key", "IK");

        Order order = new Order(orderedItems, contacts, "C.O.D");
        order.setId(1L);
        order.setTrackNumber("123456789101");
        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setClient(client);
        order.setCreatedOn(new Date(2020, Calendar.JUNE, 24));
        order.setLastUpdate(new Date(2020, Calendar.JUNE, 24));
        this.order = order;
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

    @Test
    public void shouldGetId() {
        assertThat(order.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetOrderedItems() {
        assertThat(order.getOrderedItems()).isEqualTo(Collections.singleton(orderedItem));
    }

    @Test
    public void shouldGetContacts() {
        assertThat(order.getContacts()).isEqualTo(contacts);
    }

    @Test
    public void shouldGetOrderStatus() {
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    public void shouldGetPaymentMethod() {
        assertThat(order.getPaymentMethod()).isEqualTo("C.O.D");
    }

    @Test
    public void shouldGetTrackNumber() {
        assertThat(order.getTrackNumber()).isEqualTo("123456789101");
    }

    @Test
    public void shouldGetClient() {
        assertThat(order.getClient()).isEqualTo(client);
    }

    @Test
    public void shouldGetCreatedOn() {
        assertThat(order.getCreatedOn()).isEqualTo(new Date(2020, Calendar.JUNE, 24));
    }

    @Test
    public void shouldGetLastUpdate() {
        assertThat(order.getLastUpdate()).isEqualTo(new Date(2020, Calendar.JUNE, 24));
    }

    @Test
    public void shouldEqualsIsTrue() {
        Order order = new Order(Collections.singleton(orderedItem), contacts, "C.O.D");

        assertThat(this.order.equals(order)).isTrue();
    }

    @Test
    public void hashCodeTest() {
        assertThat(order.hashCode())
                .isEqualTo(Objects.hash(
                        Collections.singleton(orderedItem),
                        contacts,
                        "C.O.D"));
    }
}
