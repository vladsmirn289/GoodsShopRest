package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.DTO.PageResponse;
import com.shop.GoodsShop.Exception.BadRequestException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;
    private String rootPath = "http://localhost/api/items";

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
                        rootPath + "/byName/" + name,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Item>>(){}).getBody();
    }

    @Override
    public List<Item> findByPrice(Double price) {
        logger.info("Find item by price - " + price);

        return restTemplate
                .exchange(
                        rootPath + "/byPrice/" + price,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Item>>(){}).getBody();
    }

    @Override
    public Page<Item> findBySearch(String keyword, Pageable pageable) {
        logger.info("Find item by keyword - " + keyword);

        PageResponse<Item> response = restTemplate
                .exchange(
                        rootPath + "/byKeyword/" + keyword + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<Item>>(){}).getBody();

        return response.toPageImpl();
    }

    @Override
    public Item findById(Long id) {
        logger.info("Find item by id - " + id);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/" + id,
                            HttpMethod.GET,
                            null,
                            Item.class).getBody();
        } catch (BadRequestException | HttpClientErrorException ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public Item findByCode(String code) {
        logger.info("Find item by code - " + code);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/byCode/" + code,
                            HttpMethod.GET,
                            null,
                            Item.class).getBody();
        } catch (Exception ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public void save(Item item) {
        if (item.getId() != null) {
            logger.info("Updating item with id - " + item.getId());
            restTemplate
                    .exchange(
                            rootPath + "/" + item.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(item),
                            Item.class);

            return;
        }

        logger.info("Create new item with name - " + item.getName());
        restTemplate
                .exchange(
                        rootPath,
                        HttpMethod.POST,
                        new HttpEntity<>(item),
                        Item.class);
    }

    @Override
    public void delete(Item item) {
        logger.info("Deleting item with id - " + item.getId());

        restTemplate
                .exchange(
                        rootPath + "/" + item.getId(),
                        HttpMethod.DELETE,
                        null,
                        Object.class);
    }
}
