package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.OrderedItem;

public interface OrderedItemService {
    void save(OrderedItem order);
    void delete(OrderedItem order);
    void deleteById(Long id);
}
