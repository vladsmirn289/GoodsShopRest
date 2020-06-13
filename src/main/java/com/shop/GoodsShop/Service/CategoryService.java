package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;

public interface CategoryService {
    void save(Category category);
    void delete(Category category);
    void deleteById(Long id);
}
