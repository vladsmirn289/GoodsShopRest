package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;

import javax.servlet.http.Cookie;

public interface ClientItemService {
    double generalPrice(Long clientId, Cookie jwtCookie);
    double generalWeight(Long clientId, Cookie jwtCookie);

    ClientItem findById(Long clientId, Long itemId, Cookie jwtCookie);

    void save(ClientItem clientItem, Long clientId, Cookie jwtCookie);
}
