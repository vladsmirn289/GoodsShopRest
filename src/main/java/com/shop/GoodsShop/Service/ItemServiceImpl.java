package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Item> findByName(String name) {
        logger.info("Find item by name - " + name);

        return restTemplate
                .exchange(
                        "/api/items/byName/" + name,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Item>>(){}).getBody();
    }

    @Override
    public List<Item> findByPrice(Double price) {
        logger.info("Find item by price - " + price);

        return restTemplate
                .exchange(
                        "/api/items/byPrice/" + price,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Item>>(){}).getBody();
    }

    @Override
    public Page<Item> findBySearch(String keyword, Pageable pageable) {
        logger.info("Find item by keyword - " + keyword);

        return restTemplate
                .exchange(
                        "/api/items/byKeyword/" + keyword,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Page<Item>>(){}).getBody();
    }

    @Override
    public Item findById(Long id) {
        logger.info("Find item by id - " + id);

        return restTemplate
                .exchange(
                        "/api/items/" + id,
                        HttpMethod.GET,
                        null,
                        Item.class).getBody();
    }

    @Override
    public Item findByCode(String code) {
        logger.info("Find item by code - " + code);

        return restTemplate
                .exchange(
                        "/api/items/byCode/" + code,
                         HttpMethod.GET,
                        null,
                        Item.class).getBody();
    }

    @Override
    public void save(Item item) {
        if (item.getId() != null) {
            logger.info("Updating item with id - " + item.getId());
            restTemplate
                    .exchange(
                            "/api/items/" + item.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(item),
                            Item.class);

            return;
        }

        logger.info("Create new item with name - " + item.getName());
        restTemplate
                .exchange(
                        "/api/items",
                        HttpMethod.POST,
                        new HttpEntity<>(item),
                        Item.class);
    }

    @Override
    public void delete(Item item) {
        logger.info("Deleting item with id - " + item.getId());

        restTemplate
                .exchange(
                        "/api/items/" + item.getId(),
                        HttpMethod.DELETE,
                        null,
                        Object.class);
    }
}
