package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("deprecation")
public class ItemValidationTest {
    private Validator validator;
    private Item item;
    private Category books;

    @BeforeEach
    void init() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean;

        this.books = new Category("Books");
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", "1234567", books);
        Image image = new Image();
        image.setImage("1234".getBytes());
        item.setId(1L);
        item.setImage("123".getBytes());
        item.setAdditionalImages(Collections.singleton(image));
        item.setCreatedOn(new Date(2020, Calendar.JUNE, 24));
        this.item = item;
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
    void shouldNotValidateWithBlankCharacteristics() {
        item.setCharacteristics("   ");
        Set<ConstraintViolation<Item>> constraintViolations = validator.validate(item);
        assertThat(constraintViolations).hasSize(1);

        ConstraintViolation<Item> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("characteristics");
        assertThat(violation.getMessage()).isEqualTo("Characteristics cannot be empty");
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

    @Test
    public void shouldGetId() {
        assertThat(item.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetName() {
        assertThat(item.getName()).isEqualTo("Spring 5");
    }

    @Test
    public void shouldGetCount() {
        assertThat(item.getCount()).isEqualTo(50L);
    }

    @Test
    public void shouldGetWeight() {
        assertThat(item.getWeight()).isEqualTo(1.592D);
    }

    @Test
    public void shouldGetPrice() {
        assertThat(item.getPrice()).isEqualTo(3300D);
    }

    @Test
    public void shouldGetDescription() {
        assertThat(item.getDescription()).isEqualTo("..........");
    }

    @Test
    public void shouldGetCharacteristics() {
        assertThat(item.getCharacteristics()).isEqualTo("....");
    }

    @Test
    public void shouldGetImage() {
        assertThat(item.getImage()).isEqualTo("123".getBytes());
    }

    @Test
    public void shouldGetAdditionalImage() {
        assertThat(item.getAdditionalImages().iterator().next().getImage())
                .isEqualTo("1234".getBytes());
    }

    @Test
    public void shouldGetCode() {
        assertThat(item.getCode()).isEqualTo("1234567");
    }

    @Test
    public void shouldGetCategory() {
        assertThat(item.getCategory()).isEqualTo(books);
    }

    @Test
    public void shouldGetCreatedOn() {
        assertThat(item.getCreatedOn()).isEqualTo(new Date(2020, Calendar.JUNE, 24));
    }

    @Test
    public void shouldEqualsIsTrue() {
        Item item = new Item("Spring 5", 50L, 1.592D, 3300D,
                "..........", "....", "1234567", books);

        assertThat(this.item.equals(item)).isTrue();
    }

    @Test
    public void hashCodeTest() {
        assertThat(this.item.hashCode())
                .isEqualTo(Objects.hash(
                        "Spring 5",
                        50L,
                        1.592D,
                        3300D,
                        "..........",
                        "....",
                        "1234567"));
    }
}
