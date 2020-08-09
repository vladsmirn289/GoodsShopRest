package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.Cookie;

public interface OrderService {
    Page<Order> findOrdersForManagers(Pageable pageable, Cookie jwtCookie);
    Order findById(Long orderId, Long clientId, Cookie jwtCookie);
    Order findByIdForManagers(Long orderId, Cookie jwtCookie);

    void save(Order order, Long clientId, Cookie jwtCookie);

    void delete(Order order, Long clientId, Cookie jwtCookie);
    void clearOrders(Long clientId, Cookie jwtCookie);
}
