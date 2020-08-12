package com.shop.GoodsShop.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Данный ресурс не найден";
    }
}
