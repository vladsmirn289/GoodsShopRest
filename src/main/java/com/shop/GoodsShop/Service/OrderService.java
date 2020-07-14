package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> findOrdersForManagers(Pageable pageable);
    Order findById(Long id);

    void save(Order order);

    void delete(Order order);
    void deleteById(Long id);
}
