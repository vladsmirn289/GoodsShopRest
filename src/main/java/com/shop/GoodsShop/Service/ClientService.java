package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ClientService extends UserDetailsService {
    Client findByLogin(String login);

    void save(Client client);

    void delete(Client client);
    void deleteById(Long id);
}
