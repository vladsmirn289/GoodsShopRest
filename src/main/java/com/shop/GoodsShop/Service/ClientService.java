package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;

public interface ClientService {
    void save(Client client);
    void delete(Client client);
    void deleteById(Long id);
}
