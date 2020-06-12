package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Set<Item> orderedItems = new HashSet<>();

    @Positive(message = "Order count must be greater than 0")
    private Long orderedCount;

    @NotNull(message = "Contacts must be set")
    private Contacts contacts;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;


    protected Order() {}

    public Order(Set<Item> orderedItems,
                 Long orderedCount,
                 Contacts contacts) {
        this.orderedItems = orderedItems;
        this.orderedCount = orderedCount;
        this.contacts = contacts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Set<Item> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public Long getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(Long orderedCount) {
        this.orderedCount = orderedCount;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderedItems.equals(order.orderedItems) &&
                orderedCount.equals(order.orderedCount) &&
                contacts.equals(order.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedItems, orderedCount, contacts);
    }
}
