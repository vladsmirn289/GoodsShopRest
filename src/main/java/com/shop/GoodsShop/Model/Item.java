package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @PositiveOrZero(message = "Количество должно быть положительным числом, или равно нулю")
    private Long count;

    @Positive(message = "Вес должен быть положительным")
    private Double weight;

    @Positive(message = "Цена должна быть положительной")
    private Double price;

    @Size(min = 10, max = 50_000, message = "Описание должно быть от 10 символов")
    private String description;

    @NotBlank(message = "Характеристики должны быть заданы")
    @Size(max = 50_000)
    private String characteristics;

    @Lob
    private byte[] image;

    @OneToMany(fetch = FetchType.EAGER,
               cascade = {CascadeType.REMOVE})
    private Set<Image> additionalImages = new HashSet<>();

    @NotBlank(message = "Код товара должен быть задан")
    private String code;

    @NotNull(message = "Категория должна существовать")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;


    protected Item() {}

    public Item(String name,
                Long count,
                Double weight,
                Double price,
                String description,
                String characteristics,
                String code,
                Category category) {
        this.name = name;
        this.count = count;
        this.weight = weight;
        this.price = price;
        this.description = description;
        this.characteristics = characteristics;
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

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Set<Image> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(Set<Image> additionalImages) {
        this.additionalImages = additionalImages;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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
                characteristics.equals(item.characteristics) &&
                code.equals(item.code) &&
                category.equals(item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, weight, price, description, characteristics, code);
    }
}
