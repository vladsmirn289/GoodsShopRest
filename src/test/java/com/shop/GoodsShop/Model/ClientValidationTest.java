package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientValidationTest {
    private Validator validator;
    private Client client;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        this.client = new Client("i@gmail.com", "12345", "Igor", "Key", "IK");
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
}
