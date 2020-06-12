package com.shop.GoodsShop.Model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Email(message = "Wrong email")
    @NotBlank(message = "Email doesn't be empty")
    private String email;

    @NotBlank(message = "Password doesn't be empty")
    @Size(min = 5, message = "Password must be minimum 5 symbols")
    private String password;

    @NotBlank(message = "First name doesn't be empty")
    @Size(min = 3, message = "First name must be minimum 3 symbols")
    private String firstName;

    @NotBlank(message = "Last name doesn't be empty")
    @Size(min = 3, message = "Last name must be minimum 3 symbols")
    private String lastName;

    private String patronymic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "basket_items",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> basket = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Order> orders = new HashSet<>();


    protected Client() {}

    public Client(String email,
                  String password,
                  String firstName,
                  String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client(String email,
                  String password,
                  String firstName,
                  String lastName,
                  String patronymic) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Set<Item> getBasket() {
        return basket;
    }

    public void setBasket(Set<Item> basket) {
        this.basket = basket;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
