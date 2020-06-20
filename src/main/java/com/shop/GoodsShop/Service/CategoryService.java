package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findByParent(Category parent);
    List<Category> findByParentIsNull();
    Category findById(Long id);

    void save(Category category);

    void delete(Category category);
    void deleteById(Long id);
}
