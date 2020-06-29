package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;

import java.util.Set;

public interface ClientItemService {
    ClientItem findById(Long id);

    void save(ClientItem clientItem);

    void delete(ClientItem clientItem);
    void deleteById(Long id);
    void deleteSetItems(Set<ClientItem> clientItemSet);
}
