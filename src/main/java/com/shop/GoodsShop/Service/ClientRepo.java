package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByLogin(String login);
}
