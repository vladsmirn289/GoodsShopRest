package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;

import java.util.List;

public interface ItemService {
    List<Item> findByName(String name);
    List<Item> findByPrice(Double price);
    List<Item> findByCategory(Category category);
    Item findByCode(String code);

    void save(Item item);

    void delete(Item item);
    void deleteById(Long id);
}
