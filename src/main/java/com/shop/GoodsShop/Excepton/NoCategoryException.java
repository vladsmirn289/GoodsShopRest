package com.shop.GoodsShop.Excepton;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCategoryException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Такой категории не существует!";
    }
}
