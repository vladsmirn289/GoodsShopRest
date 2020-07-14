package com.shop.GoodsShop.Repositories;

import com.shop.GoodsShop.Model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Page<Client> findAll(Pageable pageable);
    Client findByLogin(String login);
    Client findByConfirmationCode(String confirmationCode);
}
