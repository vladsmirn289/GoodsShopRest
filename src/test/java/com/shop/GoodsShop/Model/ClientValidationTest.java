package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientValidationTest {
    private Validator validator;
    private Client client;
    private Order order;
    private Item item;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        this.item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", "1234567", books);
        OrderedItem orderedItem = new OrderedItem(item, 1);
        Contacts contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");
        this.order = new Order(Collections.singleton(orderedItem), contacts, "C.O.D");

        Client client = new Client("i@gmail.com", "12345",
                "Igor", "Key", "IK");
        client.setPatronymic("P");
        client.setId(1L);
        client.setBasket(Collections.singleton(item));
        client.setOrders(Collections.singleton(order));
        client.setRoles(Collections.singleton(Role.USER));
        this.client = client;
    }

    @Test
    void ShouldValidateWithCorrectData() {
        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void ShouldNotValidateWithSmallFirstName() {
        client.setFirstName("K");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("First name must be minimum 3 symbols");
    }

    @Test
    void ShouldNotValidateWithSmallLastName() {
        client.setLastName("K");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
        assertThat(violation.getMessage()).isEqualTo("Last name must be minimum 3 symbols");
    }

    @Test
    void ShouldNotValidateWithBlankLogin() {
        client.setLogin("  ");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("login");
        assertThat(violation.getMessage()).isEqualTo("Login cannot be empty");
    }

    @Test
    void ShouldNotValidateWithSmallPassword() {
        client.setPassword("123");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
        assertThat(violation.getMessage()).isEqualTo("Password must be minimum 5 symbols");
    }

    @Test
    void ShouldNotValidateWithNullEmail() {
        client.setEmail(null);

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Email cannot be empty");
    }

    @Test
    void ShouldNotValidateWithEmptyEmail() {
        client.setEmail("");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Email cannot be empty");
    }

    @Test
    void ShouldNotValidateWithIncorrectEmail() {
        client.setEmail("iw");

        Set<ConstraintViolation<Client>> constraintViolations = validator.validate(client);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Client> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("Wrong email");
    }

    @Test
    void shouldGetId() {
        assertThat(client.getId()).isEqualTo(1L);
    }

    @Test
    void shouldGetFirstName() {
        assertThat(client.getFirstName()).isEqualTo("Igor");
    }

    @Test
    void shouldGetLastName() {
        assertThat(client.getLastName()).isEqualTo("Key");
    }

    @Test
    void shouldGetLogin() {
        assertThat(client.getLogin()).isEqualTo("IK");
    }

    @Test
    void shouldGetPassword() {
        assertThat(client.getPassword()).isEqualTo("12345");
    }

    @Test
    void shouldGetEmail() {
        assertThat(client.getEmail()).isEqualTo("i@gmail.com");
    }

    @Test
    void shouldGetPatronymic() {
        assertThat(client.getPatronymic()).isEqualTo("P");
    }

    @Test
    void shouldGetBasket() {
        assertThat(client.getBasket()).isEqualTo(Collections.singleton(item));
    }

    @Test
    void shouldGetOrders() {
        assertThat(client.getOrders()).isEqualTo(Collections.singleton(order));
    }

    @Test
    void shouldGetRoles() {
        assertThat(client.getRoles()).isEqualTo(Collections.singleton(Role.USER));
    }

    @Test
    void isAdminTest() {
        assertThat(client.isAdmin()).isFalse();

        client.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.ADMIN)));
        assertThat(client.isAdmin()).isTrue();
    }
}
