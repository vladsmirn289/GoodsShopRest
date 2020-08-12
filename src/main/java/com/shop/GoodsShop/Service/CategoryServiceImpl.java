package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.DTO.PageResponse;
import com.shop.GoodsShop.Exception.BadRequestException;
import com.shop.GoodsShop.Model.Category;
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
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private RestTemplate restTemplate;
    private String rootPath = "http://localhost/api/categories";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Category> findByParent(Category parent) {
        logger.info("Find categories by parent with id - " + parent.getId());
        return restTemplate
                .exchange(
                        rootPath + "/parents/" + parent.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Category>>(){}).getBody();
    }

    @Override
    public List<Category> findByParentIsNull() {
        logger.info("Find parent categories");
        return restTemplate
                .exchange(
                        rootPath + "/parents",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Category>>(){}).getBody();
    }

    @Override
    public Category findById(Long id) {
        logger.info("Find category by id - " + id);
        try {
            return restTemplate
                    .exchange(
                            rootPath + "/" + id,
                            HttpMethod.GET,
                            null,
                            Category.class).getBody();
        } catch (BadRequestException | HttpClientErrorException ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public Category findByName(String name) {
        logger.info("Find category by name - " + name);
        try {
            return restTemplate
                    .exchange(
                            rootPath + "/byName/" + name,
                            HttpMethod.GET,
                            null,
                            Category.class).getBody();
        } catch (Exception ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public List<String> getAllNamesOfRootCategories() {
        logger.info("Find all names of root categories");
        return restTemplate
                .exchange(
                        rootPath + "/allRootNames",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>(){}).getBody();
    }

    @Override
    public List<String> getAllNamesOfChildren() {
        logger.info("Find all names of children categories");
        return restTemplate
                .exchange(
                        rootPath + "/allChildNames",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>(){}).getBody();
    }

    @Override
    public Page<Item> getAllItemsByCategory(Category category, Pageable pageable) {
        logger.info("Find all names of children categories");
        PageResponse<Item> response = restTemplate
                .exchange(
                        rootPath + "/" + category.getId() + "/items?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponse<Item>>(){}).getBody();

        return response.toPageImpl();
    }

    @Override
    public void save(Category category) {
        if (category.getId() != null) {
            logger.info("Updating category with id - " + category.getId());
            restTemplate
                    .exchange(
                            rootPath + "/" + category.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(category),
                            Category.class);
            return;
        }

        logger.info("Saving category with name - " + category.getName());
        restTemplate
                .exchange(
                        rootPath,
                        HttpMethod.POST,
                        new HttpEntity<>(category),
                        Category.class);
    }

    @Override
    public void delete(Category category) {
        logger.info("Deleting category with id - " + category.getId());
        restTemplate
                .exchange(
                        rootPath + "/" + category.getId(),
                        HttpMethod.DELETE,
                        null,
                        Object.class);
    }
}
