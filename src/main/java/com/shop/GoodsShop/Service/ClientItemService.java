package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;

import java.util.Set;

public interface ClientItemService {
    double generalPrice(Set<ClientItem> basket);
    double generalWeight(Set<ClientItem> basket);

    ClientItem findById(Long id);

    void save(ClientItem clientItem);

    void delete(ClientItem clientItem);
    void deleteById(Long id);
    void deleteSetItems(Set<ClientItem> clientItemSet);
}
