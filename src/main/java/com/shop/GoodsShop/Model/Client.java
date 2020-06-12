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
}
