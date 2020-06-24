package com.shop.GoodsShop.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 3, message = "First name must be minimum 3 symbols")
    private String firstName;

    @Size(min = 3, message = "Last name must be minimum 3 symbols")
    private String lastName;

    @NotBlank(message = "Login cannot be empty")
    private String login;

    @Size(min = 5, message = "Password must be minimum 5 symbols")
    private String password;

    @Email(message = "Wrong email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    private String patronymic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "basket_items",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> basket = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.REMOVE)
    private Set<Order> orders = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_roles",
            joinColumns = @JoinColumn(name = "client_id"))
    private Set<Role> roles;


    protected Client() {}

    public Client(String email,
                  String password,
                  String firstName,
                  String lastName,
                  String login) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public Client(String email,
                  String password,
                  String firstName,
                  String lastName,
                  String patronymic,
                  String login) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
