package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidation {
    private Validator validator;
    private Book book;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        Category books = new Category("Books");
        this.book = new Book("Spring 5", 50L, 1.592D, 3300D,
                "..........", UUID.randomUUID().toString(), books, "James", "K", 150, "123");
    }

    @Test
    void shouldValidateWithCorrectData() {
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    void shouldNotValidateWithBlankAuthor() {
        book.setAuthor("  ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("author");
        assertThat(violation.getMessage()).isEqualTo("Author must be set");
    }

    @Test
    void shouldNotValidateWithBlankPublisher() {
        book.setPublisher("  ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("publisher");
        assertThat(violation.getMessage()).isEqualTo("Publisher must be set");
    }

    @Test
    void shouldNotValidateWithNegativePages() {
        book.setPages(-1);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("pages");
        assertThat(violation.getMessage()).isEqualTo("Pages cannot be negative or zero");
    }

    @Test
    void shouldNotValidateWithZeroPages() {
        book.setPages(0);
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("pages");
        assertThat(violation.getMessage()).isEqualTo("Pages cannot be negative or zero");
    }

    @Test
    void shouldNotValidateWithBlankISBN() {
        book.setISBN("   ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(book);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("ISBN");
        assertThat(violation.getMessage()).isEqualTo("ISBN number must be set");
    }
}
