package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @PositiveOrZero(message = "Count must be positive or zero")
    private Long count;

    @PositiveOrZero(message = "Weight must be positive or zero")
    private Double weight;

    private String color;

    @PositiveOrZero(message = "Price must be positive or zero")
    private Double price;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 5000, message = "Description must be between 10 and 5000 symbols")
    private String description;

    @NotBlank(message = "Code must be set")
    private String code;

    @NotNull(message = "Category cannot be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdOn;


    protected Item() {}

    public Item(String name,
                Long count,
                Double weight,
                Double price,
                String description,
                String code,
                Category category) {
        this.name = name;
        this.count = count;
        this.weight = weight;
        this.price = price;
        this.description = description;
        this.code = code;
        this.category = category;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                count.equals(item.count) &&
                weight.equals(item.weight) &&
                price.equals(item.price) &&
                description.equals(item.description) &&
                code.equals(item.code) &&
                category.equals(item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, weight, price, description, code);
    }
}
