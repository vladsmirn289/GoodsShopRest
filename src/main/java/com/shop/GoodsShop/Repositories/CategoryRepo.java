package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByParent(Category parent);
    List<Category> findByParentIsNull();
}
