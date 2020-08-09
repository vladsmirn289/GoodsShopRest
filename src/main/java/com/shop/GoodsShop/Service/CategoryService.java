package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> findByParent(Category parent);
    List<Category> findByParentIsNull();
    Category findById(Long id);
    Category findByName(String name);
    List<String> getAllNamesOfRootCategories();
    List<String> getAllNamesOfChildren();
    Page<Item> getAllItemsByCategory(Category category, Pageable pageable);

    void save(Category category);

    void delete(Category category);
}
