package com.shop.GoodsShop.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Объект имеет неверное состояние";
    }
}
