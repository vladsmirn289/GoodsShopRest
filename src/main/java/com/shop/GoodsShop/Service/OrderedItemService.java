package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.OrderedItem;

public interface OrderedItemService {
    void save(OrderedItem orderedItem);
    void delete(OrderedItem orderedItem);
    void deleteById(Long id);
}
