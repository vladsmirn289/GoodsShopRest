package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name doesn't be empty")
    private String name;

    @PositiveOrZero(message = "Count must be positive or zero")
    private Long count;

    @PositiveOrZero(message = "Weight must be positive or zero")
    private Double weight;

    @PositiveOrZero(message = "Price must be positive or zero")
    private Double price;

    @NotBlank(message = "Description doesn't be empty")
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 symbols")
    private String description;

    @NotBlank(message = "Code must be set")
    private String code;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdOn;

    protected Item() {}
}
