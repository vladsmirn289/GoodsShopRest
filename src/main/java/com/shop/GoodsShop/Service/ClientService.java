package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
import com.shop.GoodsShop.Model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Set;

public interface ClientService extends UserDetailsService {
    Client findById(Long clientId, Cookie jwtCookie);
    Page<Client> findAll(Pageable pageable, Cookie jwtCookie);
    Client findByLogin(String login, Cookie jwtCookie);
    Client findByConfirmationCode(String confirmationCode, Cookie jwtCookie);
    List<ClientItem> findBasketItemsByClientId(Long clientId, Cookie jwtCookie);
    Page<Order> findOrdersByClientId(Long clientId, Pageable pageable, Cookie jwtCookie);

    void authenticateClient(String rawPassword, String login, AuthenticationManager authManager);

    void save(Client client, Cookie jwtCookie);

    void delete(Client client, Cookie jwtCookie);
    void deleteBasketItems(Set<ClientItem> itemSet, Long clientId, Cookie jwtCookie);
    void clearBasket(Long clientId, Cookie jwtCookie);
}
