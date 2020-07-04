package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findOrdersForManagers();
    Order findById(Long id);

    void save(Order order);

    void delete(Order order);
    void deleteById(Long id);
}
