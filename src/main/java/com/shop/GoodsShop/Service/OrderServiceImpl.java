package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<Order> findOrdersForManagers(Pageable pageable, String jwt) {
        logger.info("Find orders for managers");
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/managerOrders",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Page<Order>>(){}).getBody();
    }

    @Override
    public Order findById(Long orderId, Long clientId, String jwt) {
        logger.info("Find order by id - " + orderId + " and client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders/" + orderId,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Order.class).getBody();
    }

    @Override
    public Order findByIdForManagers(Long orderId, String jwt) {
        logger.info("Find order by id - " + orderId + " for managers");
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/orders/" + orderId,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Order.class).getBody();
    }

    @Override
    public void save(Order order, Long clientId, String jwt) {
        HttpHeaders headers = getJwtHeader(jwt);

        if (order.getId() != null) {
            logger.info("Updating order with id - " + order.getId());
            restTemplate
                    .exchange(
                            "/api/clients/" + clientId + "/orders/" + order.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(order, headers),
                            Order.class);

            return;
        }

        logger.info("Create new order with client id - " + clientId);
        restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders",
                        HttpMethod.POST,
                        new HttpEntity<>(order, headers),
                        Order.class);
    }

    @Override
    public void delete(Order order, Long clientId, String jwt) {
        logger.info("Deleting order with id - " + order.getId() + " and client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders/" + order.getId(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(headers),
                        Object.class);
    }

    @Override
    public void clearOrders(Long clientId, String jwt) {
        logger.info("Clear orders with client id " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders",
                        HttpMethod.DELETE,
                        new HttpEntity<>(headers),
                        Object.class);
    }

    private HttpHeaders getJwtHeader(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return headers;
    }
}
