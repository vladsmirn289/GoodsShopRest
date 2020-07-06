package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> findByParent(Category parent);
    List<Category> findByParentIsNull();
    Category findById(Long id);
    Category findByName(String name);
    Set<String> getAllNamesOfCategories();

    void save(Category category);

    void delete(Category category);
    void deleteById(Long id);
}
