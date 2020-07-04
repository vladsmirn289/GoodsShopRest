package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Service.InitDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepoTest {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ClientItemRepo clientItemRepo;

    @MockBean
    private InitDB initDB;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        ClientItem clientItem = new ClientItem(item, 2);
        Contacts contacts = new Contacts("123456", "Russia", "Moscow", "...", "89441234567");

        Order order = new Order(new HashSet<>(Collections.singleton(clientItem)), contacts, "C.O.D");
        order.setOrderStatus(OrderStatus.COMPLETED);

        Order order1 = new Order(new HashSet<>(Collections.singleton(clientItem)), contacts, "C.O.D");
        order1.setOrderStatus(OrderStatus.ON_THE_WAY);

        categoryRepo.save(books);
        categoryRepo.save(book);
        itemRepo.save(item);
        clientItemRepo.save(clientItem);
        orderRepo.save(order);
        orderRepo.save(order1);
    }

    @AfterEach
    public void resetSequence() {
        entityManager.getEntityManager()
                .createNativeQuery("alter sequence hibernate_sequence restart 1")
                .executeUpdate();
    }

    @Test
    public void shouldFindOrderByStatusIsNotCompleted() {
        List<Order> orders = orderRepo.findOrdersForManagers();

        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getId()).isEqualTo(6L);
    }

    @Test
    public void shouldFindOrderById() {
        Order order = orderRepo.findById(5L).orElse(null);

        assertThat(order).isNotNull();
        assertThat(order.getClientItems().iterator().next().getItem().getName()).isEqualTo("item");
        assertThat(order.getContacts().getZipCode()).isEqualTo("123456");
        assertThat(order.getPaymentMethod()).isEqualTo("C.O.D");
    }

    @Test
    public void shouldSaveOrder() {
        Category laptops = new Category("Laptops");
        Category laptop = new Category("Laptop", laptops);
        Item item = new Item("laptop", 40L, 2D
                , 56000D, "LaptopDescription..."
                , "LaptopCharacteristics...", "567", laptop);
        ClientItem clientItem = new ClientItem(item, 1);
        Contacts contacts = new Contacts("789101", "Russia", "Moscow", "...", "89441234567");
        Order order = new Order(new HashSet<>(Collections.singleton(clientItem)), contacts, "C.O.D");

        categoryRepo.save(laptops);
        categoryRepo.save(laptop);
        itemRepo.save(item);
        clientItemRepo.save(clientItem);
        orderRepo.save(order);

        assertThat(orderRepo.findAll().size()).isEqualTo(3);
        Order order1 = orderRepo.findById(11L).orElse(null);

        assertThat(order).isNotNull();
        assertThat(order.getClientItems().iterator().next().getItem().getName()).isEqualTo("laptop");
        assertThat(order.getContacts().getZipCode()).isEqualTo("789101");
        assertThat(order.getPaymentMethod()).isEqualTo("C.O.D");
    }

    @Test
    public void shouldDeleteOrder() {
        Order order = orderRepo.findById(5L).orElse(null);
        assertThat(order).isNotNull();
        orderRepo.delete(order);

        assertThat(orderRepo.findAll().size()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteOrderById() {
        orderRepo.deleteById(5L);

        assertThat(orderRepo.findAll().size()).isEqualTo(1);
    }
}
