package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;

public interface OrderService {
    void save(Order order);
    void delete(Order order);
    void deleteById(Long id);
}
