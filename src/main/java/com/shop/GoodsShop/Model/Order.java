package com.shop.GoodsShop.Model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Set<Item> ordered = new HashSet<>();

    @Positive(message = "Order count must be greater than 0")
    private Long orderedCount;

    private Contacts contacts;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;

    protected Order() {}
}
