package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
