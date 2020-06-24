package com.shop.GoodsShop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageValidationTest {
    private Image image;

    @BeforeEach
    public void init() {
        Image image = new Image();
        image.setId(1L);
        image.setImage("123".getBytes());

        this.image = image;
    }

    @Test
    public void shouldGetId() {
        assertThat(image.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldGetImage() {
        assertThat(image.getImage()).isEqualTo("123".getBytes());
    }

    @Test
    public void shouldEqualsIsTrue() {
        Image image = new Image();
        image.setId(2L);
        image.setImage("123".getBytes());

        assertThat(this.image.equals(image)).isTrue();
    }

    @Test
    public void hashCodeTest() {
        assertThat(this.image.hashCode()).isEqualTo(Arrays.hashCode("123".getBytes()));
    }
}
