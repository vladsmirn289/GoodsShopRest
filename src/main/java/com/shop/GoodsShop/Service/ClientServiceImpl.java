package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.DTO.PageResponse;
import com.shop.GoodsShop.Exception.BadRequestException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private RestTemplate restTemplate;
    private ClientRepo clientRepo;
    private String rootPath = "http://localhost/api/clients";

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
    public Client findById(Long clientId, String jwt) {
        logger.info("Find client by id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/" + clientId,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            Client.class).getBody();
        } catch (BadRequestException | HttpClientErrorException ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public Page<Client> findAll(Pageable pageable, String jwt) {
        logger.info("Find all clients");
        HttpHeaders headers = getJwtHeader(jwt);

        PageResponse<Client> response = restTemplate
                .exchange(
                        rootPath + "?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<PageResponse<Client>>(){}).getBody();

        return response.toPageImpl();
    }

    @Override
    public Client findByLogin(String login, String jwt) {
        logger.info("Find client by login - " + login);
        HttpHeaders headers = getJwtHeader(jwt);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/byLogin/" + login,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            Client.class).getBody();
        } catch (Exception ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public Client findByConfirmationCode(String confirmationCode, String jwt) {
        logger.info("Find client by confirm code - " + confirmationCode);
        HttpHeaders headers = getJwtHeader(jwt);

        try {
            return restTemplate
                    .exchange(
                            rootPath + "/byConfirmCode/" + confirmationCode,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            Client.class).getBody();
        } catch (Exception ex) {
            logger.warn(ex.toString());
            return null;
        }
    }

    @Override
    public List<ClientItem> findBasketItemsByClientId(Long clientId, String jwt) {
        logger.info("Find basket items by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        return restTemplate
                .exchange(
                        rootPath + "/" + clientId + "/basket",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<ClientItem>>(){}).getBody();
    }

    @Override
    public Page<Order> findOrdersByClientId(Long clientId, Pageable pageable, String jwt) {
        logger.info("Find orders by client id - " + clientId);
        HttpHeaders headers = getJwtHeader(jwt);

        PageResponse<Order> response = restTemplate
                .exchange(
                        rootPath + "/" + clientId + "/orders?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<PageResponse<Order>>(){}).getBody();

        return response.toPageImpl();
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
                            rootPath + "/" + client.getId(),
                            HttpMethod.PUT,
                            new HttpEntity<>(client, headers),
                            Client.class);

            return;
        }

        logger.info("Create new client with login - " + client.getLogin());
        restTemplate
                .exchange(
                        rootPath,
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
                        rootPath + "/" + client.getId(),
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
                            rootPath + "/" + clientId + "/basket/" + ci.getId(),
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
                        rootPath + "/" + clientId + "/basket",
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
            throw new UsernameNotFoundException("Client with login - " + login + " not found");
        }

        return client;
    }

    private HttpHeaders getJwtHeader(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return headers;
    }
}
