package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Config.JWT.JwtUtils;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;
    private ClientRepo clientRepo;
    private JwtUtils jwtUtils;

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

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Client findById(Long clientId, String jwt) {
        logger.info("Find client by id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public Page<Client> findAll(Pageable pageable, String jwt) {
        logger.info("Find all clients");
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Page<Client>>(){}).getBody();
    }

    @Override
    public Client findByLogin(String login, String jwt) {
        logger.info("Find client by login - " + login);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/byLogin/" + login,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public Client findByConfirmationCode(String confirmationCode, String jwt) {
        logger.info("Find client by confirm code - " + confirmationCode);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/byConfirmCode/" + confirmationCode,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Client.class).getBody();
    }

    @Override
    public List<ClientItem> findBasketItemsByClientId(Long clientId, String jwt) {
        logger.info("Find basket items by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/basket",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<ClientItem>>(){}).getBody();
    }

    @Override
    public Page<Order> findOrdersByClientId(Long clientId, Pageable pageable, String jwt) {
        logger.info("Find orders by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        "/api/clients/" + clientId + "/orders?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Page<Order>>(){}).getBody();
    }

    @Override
    public void authenticateClient(AuthenticationManager authManager, String login) {
        UserDetails userDetails = clientRepo.findByLogin("CemenBukov");
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Override
    public void save(Client client, String jwt) {
        HttpHeaders headers = getJwtHeader(jwt);

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
    public void delete(Client client, String jwt) {
        logger.info("Deleting client with id - " + client.getId());
        HttpHeaders headers = getJwtHeader(jwt);

        restTemplate
                .exchange(
                        "/api/clients/" + client.getId(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(headers),
                        Object.class);
    }

    @Override
    public void deleteBasketItems(Set<ClientItem> itemSet, Long clientId, String jwt) {
        logger.info("Delete basket items with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

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
    public void clearBasket(Long clientId, String jwt) {
        logger.info("Clear basket with client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

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

    private HttpHeaders getJwtHeader(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return headers;
    }
}
