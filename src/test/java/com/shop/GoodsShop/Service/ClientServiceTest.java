package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.Client;
import com.shop.GoodsShop.Repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @MockBean
    private InitDB initDB;

    @MockBean
    private ClientRepo clientRepo;

    private Client client;

    @BeforeEach
    public void init() {
        this.client = new Client("f@f","123456", "ABC", "DEF", "A");
    }

    @Test
    public void shouldFindClientByLogin() {
        Mockito
                .doReturn(client)
                .when(clientRepo)
                .findByLogin("A");

        Client client = clientService.findByLogin("A");

        assertThat(client.getLogin()).isEqualTo("A");
        Mockito
                .verify(clientRepo, Mockito.times(1))
                .findByLogin("A");
    }

    @Test
    public void shouldSaveClient() {
        clientService.save(client);

        Mockito.verify(clientRepo, Mockito.times(1))
                .save(client);
    }

    @Test
    public void shouldDeleteClient() {
        clientService.delete(client);

        Mockito.verify(clientRepo, Mockito.times(1))
                .delete(client);
    }

    @Test
    public void shouldDeleteClientById() {
        clientService.deleteById(1L);

        Mockito.verify(clientRepo, Mockito.times(1))
                .deleteById(1L);
    }
}
