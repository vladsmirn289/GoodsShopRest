package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Exception.BadRequestException;
import com.shop.GoodsShop.Model.ClientItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientItemServiceImpl implements ClientItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;
    private String rootPath = "http://localhost/api/clients";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Override
    public double generalPrice(Long clientId, String jwt) {
        logger.info("Count general price of basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        rootPath + "/" + clientId + "/basket/generalPrice",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Double.class).getBody();
    }

    @Override
    public double generalWeight(Long clientId, String jwt) {
        logger.info("Count general weight of basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        rootPath + "/" + clientId + "/basket/generalWeight",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Double.class).getBody();
    }

    @Override
    public ClientItem findById(Long clientId, Long itemId, String jwt) {
        logger.info("Find item in basket with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/" + clientId + "/basket/" + itemId,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            ClientItem.class).getBody();
        } catch (BadRequestException | HttpClientErrorException ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public void addToBasketOrUpdate(ClientItem clientItem, Long clientId, String jwt) {
        HttpHeaders headers = getJwtHeader(jwt);

        if (clientItem.getId() != null) {
            logger.info("Updating item in the basket with client id - " + clientId);
            restTemplate
                    .exchange(
                            rootPath + "/" + clientId + "/basket/" + clientItem.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(clientItem, headers),
                            ClientItem.class);

            return;
        }

        logger.info("Add new item in the basket with client id - " + clientId);
        restTemplate
                .exchange(
                        rootPath + "/" + clientId + "/basket",
                        HttpMethod.POST,
                        new HttpEntity<>(clientItem, headers),
                        ClientItem.class);
    }

    private HttpHeaders getJwtHeader(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return headers;
    }
}
