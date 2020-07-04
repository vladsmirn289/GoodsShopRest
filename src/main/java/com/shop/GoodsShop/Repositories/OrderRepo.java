package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.orderStatus <> 'COMPLETED'")
    List<Order> findOrdersForManagers();
}
