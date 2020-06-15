package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepo extends JpaRepository<OrderedItem, Long> {
}
