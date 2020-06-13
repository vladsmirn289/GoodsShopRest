package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactsValidation {
    private Validator validator;
    private Contacts contacts;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        this.contacts = new Contacts("123456", "Russia",
                "Moscow", "Bolotnaya street", "+7-499-123-45-67");
    }

    @Test
    void ShouldValidateWithCorrectData() {
        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void ShouldNotValidateWithSmallZipCode() {
        contacts.setZipCode("123");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("zipCode");
        assertThat(violation.getMessage()).isEqualTo("Zip code must be between 4 and 9 digits");
    }

    @Test
    void ShouldNotValidateWithBigZipCode() {
        contacts.setZipCode("1234567890");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("zipCode");
        assertThat(violation.getMessage()).isEqualTo("Zip code must be between 4 and 9 digits");
    }

    @Test
    void ShouldNotValidateWithBlankCountry() {
        contacts.setCountry("  ");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("country");
        assertThat(violation.getMessage()).isEqualTo("Country cannot be empty");
    }

    @Test
    void ShouldNotValidateWithBlankCity() {
        contacts.setCity("  ");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("city");
        assertThat(violation.getMessage()).isEqualTo("City cannot be empty");
    }

    @Test
    void ShouldNotValidateWithBlankStreet() {
        contacts.setStreet("  ");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("street");
        assertThat(violation.getMessage()).isEqualTo("Street cannot be empty");
    }

    @Test
    void ShouldNotValidateWithBlankPhoneNumber() {
        contacts.setPhoneNumber("  ");

        Set<ConstraintViolation<Contacts>> constraintViolations = validator.validate(contacts);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Contacts> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("phoneNumber");
        assertThat(violation.getMessage()).isEqualTo("Phone number cannot be empty");
    }
}
