package com.shop.GoodsShop.Config.RestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().is4xxClientError()
                || httpResponse.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Такого элемента не существует");
            } else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new IllegalStateException("Неверно задано состояние объекта");
            } else {
                throw new HttpClientErrorException(httpResponse.getStatusCode(),
                        httpResponse.getStatusText());
            }
        } else if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new HttpServerErrorException(httpResponse.getStatusCode(),
                    httpResponse.getStatusText());
        }
    }
}
