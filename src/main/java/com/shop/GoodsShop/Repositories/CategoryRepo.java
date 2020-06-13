package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
