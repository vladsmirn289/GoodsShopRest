package com.shop.GoodsShop.Excepton;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoItemException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Такого предмета не существует!";
    }
}
