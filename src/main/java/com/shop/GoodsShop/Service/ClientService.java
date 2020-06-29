package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ClientService extends UserDetailsService {
    void authenticateClient(String rawPassword, String login, AuthenticationManager authManager);

    Client findByLogin(String login);
    Client findByConfirmationCode(String confirmationCode);

    void save(Client client);

    void delete(Client client);
    void deleteById(Long id);
}
