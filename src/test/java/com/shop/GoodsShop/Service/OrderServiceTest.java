package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Repositories.OrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private OrderRepo orderRepo;

    private Order order;

    @BeforeEach
    public void init() {
        Category books = new Category("Books");
        Category book = new Category("Book", books);
        Item item = new Item("item", 30L, 3D
                , 600D, "description..."
                , "characteristics...", "123", book);
        ClientItem clientItem = new ClientItem(item, 2);
        Contacts contacts = new Contacts("123456", "Russia", "Moscow", "...", "89441234567");
        this.order = new Order(new HashSet<>(Collections.singleton(clientItem)), contacts, "C.O.D");
    }

    @Test
    public void shouldFindOrdersForManagers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orders = new PageImpl<>(Collections.singletonList(order));
        Mockito
                .doReturn(orders)
                .when(orderRepo)
                .findOrdersForManagers(pageable);

        Page<Order> orderListPage = orderService.findOrdersForManagers(pageable);
        List<Order> orderList = orderListPage.getContent();

        assertThat(orderList).isNotNull();
        assertThat(orderList).isNotEmpty();
        Mockito.verify(orderRepo, Mockito.times(1))
                .findOrdersForManagers(pageable);
    }

    @Test
    public void shouldFindOrderById() {
        Mockito
                .doReturn(Optional.of(order))
                .when(orderRepo)
                .findById(1L);

        Order order1 = orderService.findById(1L);
        
        Mockito.verify(orderRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldReturnNullWhenFindOrderByIncorrectId() {
        Order order1 = orderService.findById(1L);

        assertThat(order1).isNull();
        Mockito.verify(orderRepo, Mockito.times(1))
                .findById(1L);
    }

    @Test
    public void shouldSaveOrder() {
        orderService.save(order);

        Mockito.verify(orderRepo, Mockito.times(1))
                .save(order);
    }

    @Test
    public void shouldDeleteOrder() {
        orderService.delete(order);

        Mockito.verify(orderRepo, Mockito.times(1))
                .delete(order);
    }

    @Test
    public void shouldDeleteOrderById() {
        orderService.deleteById(1L);

        Mockito.verify(orderRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
