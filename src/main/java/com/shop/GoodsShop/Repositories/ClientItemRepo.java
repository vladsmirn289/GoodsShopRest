package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.ClientItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientItemRepo extends JpaRepository<ClientItem, Long> {
}
