package com.shop.GoodsShop.Dao;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {
}
