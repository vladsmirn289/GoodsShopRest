package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;

public interface ClientItemService {
    double generalPrice(Long clientId, String jwt);
    double generalWeight(Long clientId, String jwt);

    ClientItem findById(Long clientId, Long itemId, String jwt);

    void addToBasketOrUpdate(ClientItem clientItem, Long clientId, String jwt);
}
