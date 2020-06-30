package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Category;
import com.shop.GoodsShop.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByPrice(Double price);
    List<Item> findByCategory(Category category);
    Item findByCode(String code);

    @Query("SELECT i FROM Item i WHERE lower(i.name) LIKE lower(concat('%', :keyword, '%'))")
    List<Item> findBySearch(@Param("keyword") String keyword);
}
