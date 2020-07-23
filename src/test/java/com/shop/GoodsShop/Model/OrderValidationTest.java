package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("deprecation")
public class OrderValidationTest {
    private Validator validator;
    private Order order;
    private ClientItem clientItem;
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
        this.clientItem = new ClientItem(item, 1);
        Set<ClientItem> clientItems = new HashSet<>();
        clientItems.add(clientItem);

        this.contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");

        this.client = new Client("i@gmail.com", "12345", "Igor", "Key", "IK");

        Order order = new Order(clientItems, contacts, "C.O.D");
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
    void ShouldNotValidateWithEmptyClientItems() {
        order.setClientItems(new HashSet<>());

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("clientItems");
        assertThat(violation.getMessage()).isEqualTo("Список заказанных предметов не может быть пустым");
    }

    @Test
    void ShouldNotValidateWithNullContacts() {
        order.setContacts(null);

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("contacts");
        assertThat(violation.getMessage()).isEqualTo("Контактная информация должна быть задана");
    }

    @Test
    void ShouldNotValidateWithBlankPaymentMethod() {
        order.setPaymentMethod("   ");

        Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Order> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("paymentMethod");
        assertThat(violation.getMessage()).isEqualTo("Способ оплаты должен быть задан");
    }

    @Test
    public void getterTests() {
        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getClientItems()).isEqualTo(Collections.singleton(clientItem));
        assertThat(order.getContacts()).isEqualTo(contacts);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(order.getPaymentMethod()).isEqualTo("C.O.D");
        assertThat(order.getTrackNumber()).isEqualTo("123456789101");
        assertThat(order.getClient()).isEqualTo(client);
        assertThat(order.getCreatedOn()).isEqualTo(new Date(2020, Calendar.JUNE, 24));
        assertThat(order.getLastUpdate()).isEqualTo(new Date(2020, Calendar.JUNE, 24));
    }
}
