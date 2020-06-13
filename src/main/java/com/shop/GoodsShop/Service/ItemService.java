package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Item;

public interface ItemService {
    void save(Item item);
    void delete(Item item);
    void deleteById(Long id);
}
