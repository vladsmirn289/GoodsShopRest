package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Model.ClientItem;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;
    private ClientRepo clientRepo;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        logger.debug("Setting restTemplate");
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        logger.debug("Setting clientRepo");
        this.clientRepo = clientRepo;
    }

    @Override
    public Client findById(Long clientId, Cookie jwtCookie) {
        logger.info("Find client by id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public Page<Client> findAll(Pageable pageable, Cookie jwtCookie) {
        logger.info("Find all clients");
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Page<Client>>(){}).getBody();
    }

    @Override
    public Client findByLogin(String login, Cookie jwtCookie) {
        logger.info("Find client by login - " + login);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/byLogin/" + login,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public Client findByConfirmationCode(String confirmationCode, Cookie jwtCookie) {
        logger.info("Find client by confirm code - " + confirmationCode);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/byConfirmCode/" + confirmationCode,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public List<ClientItem> findBasketItemsByClientId(Long clientId, Cookie jwtCookie) {
        logger.info("Find basket items by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<ClientItem>>(){}).getBody();
    }

    @Override
    public Page<Order> findOrdersByClientId(Long clientId, Pageable pageable, Cookie jwtCookie) {
        logger.info("Find orders by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Page<Order>>(){}).getBody();
    }

    /* TODO: realize it */
    @Override
    public void authenticateClient(String rawPassword, String login, AuthenticationManager authManager) {

    }

    @Override
    public void save(Client client, Cookie jwtCookie) {
        HttpHeaders headers = getJwtHeader(jwtCookie);

        if (client.getId() != null) {
            logger.info("Updating client with id - " + client.getId());
            restTemplate
                    .exchange(
                            "/api/clients/" + client.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(client, headers),
                            Client.class);

            return;
        }

        logger.info("Create new client with login - " + client.getLogin());
        restTemplate
                .exchange(
                        "/api/clients",
                        HttpMethod.POST,
                        new HttpEntity<>(client),
                        Client.class);
    }

    @Override
    public void delete(Client client, Cookie jwtCookie) {
        logger.info("Deleting client with id - " + client.getId());
        HttpHeaders headers = getJwtHeader(jwtCookie);

        restTemplate
                .exchange(
                        "/api/clients/" + client.getId(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(headers),
                        Object.class);
    }

    @Override
    public void deleteBasketItems(Set<ClientItem> itemSet, Long clientId, Cookie jwtCookie) {
        logger.info("Delete basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        for (ClientItem ci : itemSet) {
            restTemplate
                    .exchange(
                            "/api/clients/" + clientId + "/basket/" + ci.getId(),
                            HttpMethod.DELETE,
                            new HttpEntity<>(headers),
                            Object.class);
        }
    }

    @Override
    public void clearBasket(Long clientId, Cookie jwtCookie) {
        logger.info("Clear basket with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwtCookie);

        restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket",
                        HttpMethod.DELETE,
                        new HttpEntity<>(headers),
                        Object.class);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("Loading user by username");
        Client client = clientRepo.findByLogin(login);

        if (client == null) {
            logger.warn("Client with login - " + login + " not found");
            return null;
        }

        return client;
    }

    private HttpHeaders getJwtHeader(Cookie jwtCookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtCookie.getValue());

        return headers;
    }
}
