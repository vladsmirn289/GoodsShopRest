package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByParent(Category parent);
    List<Category> findByParentIsNull();
}
