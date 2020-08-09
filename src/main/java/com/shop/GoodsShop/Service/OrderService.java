package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<Order> findOrdersForManagers(Pageable pageable, String jwt);
    Order findById(Long orderId, Long clientId, String jwt);
    Order findByIdForManagers(Long orderId, String jwt);

    void save(Order order, Long clientId, String jwt);

    void delete(Order order, Long clientId, String jwt);
    void clearOrders(Long clientId, String jwt);
}
