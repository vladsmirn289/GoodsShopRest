package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface ClientService extends UserDetailsService {
    void authenticateClient(String rawPassword, String login, AuthenticationManager authManager);

    Client findById(Long id);
    Page<Client> findAll(Pageable pageable);
    Client findByLogin(String login);
    Client findByConfirmationCode(String confirmationCode);

    void save(Client client);

    void delete(Client client);
    void deleteById(Long id);
    void deleteBasketItems(Set<ClientItem> itemSet, String login);
}
