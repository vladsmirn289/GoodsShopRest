package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "Ordered items cannot be empty")
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Set<Item> orderedItems = new HashSet<>();

    @Positive(message = "Order count must be greater than 0")
    private Long orderedCount;

    @NotNull(message = "Contacts must be set")
    private Contacts contacts;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotBlank(message = "Payment method cannot be empty")
    private String paymentMethod;

    private String trackNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;


    protected Order() {}

    public Order(Set<Item> orderedItems,
                 Long orderedCount,
                 Contacts contacts,
                 String paymentMethod) {
        this.orderedItems = orderedItems;
        this.orderedCount = orderedCount;
        this.contacts = contacts;
        this.paymentMethod = paymentMethod;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderedItems.equals(order.orderedItems) &&
                orderedCount.equals(order.orderedCount) &&
                contacts.equals(order.contacts) &&
                paymentMethod.equals(order.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedItems, orderedCount, contacts, paymentMethod);
    }
}
