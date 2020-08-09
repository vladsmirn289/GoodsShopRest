package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface ClientService extends UserDetailsService {
    Client findById(Long clientId, String jwt);
    Page<Client> findAll(Pageable pageable, String jwt);
    Client findByLogin(String login, String jwt);
    Client findByConfirmationCode(String confirmationCode, String jwt);
    List<ClientItem> findBasketItemsByClientId(Long clientId, String jwt);
    Page<Order> findOrdersByClientId(Long clientId, Pageable pageable, String jwt);

    void authenticateClient(AuthenticationManager authManager, String login);

    void save(Client client, String jwt);

    void delete(Client client, String jwt);
    void deleteBasketItems(Set<ClientItem> itemSet, Long clientId, String jwt);
    void clearBasket(Long clientId, String jwt);
}
