package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;

public interface ClientItemService {
    ClientItem findById(Long id);

    void save(ClientItem clientItem);

    void delete(ClientItem clientItem);
    void deleteById(Long id);
}
