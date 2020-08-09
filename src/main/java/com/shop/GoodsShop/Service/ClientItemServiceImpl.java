package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.ClientItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;

@Service
public class ClientItemServiceImpl implements ClientItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Override
    public double generalPrice(Long clientId, Cookie jwtCookie) {
        logger.info("Count general price of basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket/generalPrice",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Double.class).getBody();
    }

    @Override
    public double generalWeight(Long clientId, Cookie jwtCookie) {
        logger.info("Count general weight of basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket/generalWeight",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Double.class).getBody();
    }

    @Override
    public ClientItem findById(Long clientId, Long itemId, Cookie jwtCookie) {
        logger.info("Find item in basket with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket/" + itemId,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ClientItem.class).getBody();
    }

    @Override
    public void save(ClientItem clientItem, Long clientId, Cookie jwtCookie) {
        HttpHeaders headers = getJwtHeader(jwtCookie);

        if (clientItem.getId() != null) {
            logger.info("Updating item in the basket with client id - " + clientId);
            restTemplate
                    .exchange(
                            "/api/clients/" + clientId + "/basket/" + clientItem.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(clientItem, headers),
                            ClientItem.class);

            return;
        }

        logger.info("Add new item in the basket with client id - " + clientId);
        restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket",
                        HttpMethod.POST,
                        new HttpEntity<>(clientItem, headers),
                        ClientItem.class);
    }

    private HttpHeaders getJwtHeader(Cookie jwtCookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtCookie.getValue());

        return headers;
    }
}
